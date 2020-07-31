package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details.ContactDetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.util.Calendar
import java.util.GregorianCalendar

const val YEAR_1999 = 1999
const val YEAR_1990 = 1990

const val DAY_OF_MONTH_8 = 8
const val DAY_OF_MONTH_9 = 9

object ContactDetailsNotificationSpecification : Spek({
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }

    val testCoroutineDispatchers = TestDispatchers()

    val birthdayCalendar: BirthdayCalendar = mock()
    val currentTime: CurrentTime = mock()

    val notificationRepository: NotificationRepository = mock()
    val contactDetailsRepository: ContactDetailsRepository = mock()

    lateinit var expectedBirthdayNotification: BirthdayNotification

    val contactShortInfo = ContactShortInfo(
        "id",
        "name",
        "t1"
    )

    val currentDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9)
    val birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
    val testTriggerDate = GregorianCalendar(2000, Calendar.SEPTEMBER, DAY_OF_MONTH_8)

    val contactDetailsInfo = ContactDetailsInfo(
        contactShortInfo,
        birthdayDate,
        "t2",
        "e1",
        "e2",
        "",
        null
    )

    lateinit var notificationInteractor: NotificationInteractor

    lateinit var contactDetailsInteractor: ContactDetailsInteractor

    lateinit var contactDetailsViewModel: ContactDetailsViewModel

    fun mockCurrentTimeAndBirthdayCalendar() {
        Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
        Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)

        Mockito.`when`(contactDetailsRepository.getDetailsContact("id"))
            .thenReturn(flowOf(contactDetailsInfo))
    }

    fun initInteractors() {
        notificationInteractor = NotificationModel(
            notificationRepository,
            currentTime,
            birthdayCalendar
        )

        contactDetailsInteractor = ContactDetailsModel(
            contactDetailsRepository
        )

        contactDetailsViewModel =
            ContactDetailsViewModel(
                testCoroutineDispatchers,
                notificationInteractor,
                contactDetailsInteractor,
                "id"
            )
        contactDetailsViewModel.getContactDetails()
    }

    Feature("Я как пользователь хочу устанавливать и удалять уведомления о дне рождения контакта") {
        Scenario("Установка напоминания") {
            Given("Сущность напоминания") {
                expectedBirthdayNotification = BirthdayNotification(
                    contactDetailsInfo,
                    true,
                    testTriggerDate
                )
                Mockito.`when`(
                    notificationRepository.setBirthdayReminder(
                        contactDetailsInfo,
                        testTriggerDate
                    )
                ).thenReturn(expectedBirthdayNotification)

                mockCurrentTimeAndBirthdayCalendar()
                initInteractors()
            }
            When("Устанавливается напоминание") {
                contactDetailsViewModel.setBirthdayNotification()
            }
            Then("Напоминание установлено") {
                verify(notificationRepository).setBirthdayReminder(
                    contactDetailsInfo,
                    testTriggerDate
                )
            }
        }

        Scenario("Удаление напоминания") {
            Given("Сущность напоминания") {
                expectedBirthdayNotification = BirthdayNotification(
                    contactDetailsInfo,
                    false,
                    null
                )
                Mockito.`when`(notificationRepository.removeBirthdayReminder(contactDetailsInfo))
                    .thenReturn(expectedBirthdayNotification)

                mockCurrentTimeAndBirthdayCalendar()
                initInteractors()
            }

            When("Удаляется напоминание") {
                contactDetailsViewModel.removeBirthdayNotification()
            }
            Then("Напоминание удалено") {
                verify(notificationRepository).removeBirthdayReminder(contactDetailsInfo)
            }
        }

        Scenario("Получение статуса напоминания") {
            Given("Сущность напоминания") {
                expectedBirthdayNotification = BirthdayNotification(
                    contactDetailsInfo,
                    false,
                    null
                )
                Mockito.`when`(
                    notificationRepository.getBirthdayNotificationEntity(
                        contactDetailsInfo, null
                    )
                ).thenReturn(expectedBirthdayNotification)

                mockCurrentTimeAndBirthdayCalendar()
                initInteractors()
            }
            When("Получение статуса напоминания") {
                contactDetailsViewModel.getBirthdayNotification()
            }
            Then("Статус получен") {
                verify(notificationRepository).getBirthdayNotificationEntity(
                    contactDetailsInfo,
                    null
                )
            }
        }
    }
})
