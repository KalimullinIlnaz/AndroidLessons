package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime
import com.nhaarman.mockitokotlin2.mock
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.test.assertEquals

const val YEAR_1999 = 1999
const val YEAR_1990 = 1990

const val DAY_OF_MONTH_8 = 8
const val DAY_OF_MONTH_9 = 9

object ContactDetailsNotificationSpecification : Spek({
    val birthdayCalendar: BirthdayCalendar = mock()
    val currentTime: CurrentTime = mock()

    val notificationRepository: NotificationRepository = mock()
    val contactDetailsRepository: ContactDetailsRepository = mock()

    lateinit var expectedBirthdayNotification: BirthdayNotification
    lateinit var actualBirthdayNotification: BirthdayNotification

    val contactShortInfo = ContactShortInfo(
        "id",
        "name",
        "t1"
    )

    Feature("Тест презентера деталей контакта") {
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

        Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
        Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)

        val notificationInteractor = NotificationModel(
            notificationRepository,
            currentTime,
            birthdayCalendar
        )

        val contactDetailsInteractor = ContactDetailsModel(
            contactDetailsRepository
        )

        val contactDetailsPresenter = ContactDetailsPresenter(
            notificationInteractor,
            contactDetailsInteractor
        )

        Scenario("Установка напоминания") {
            Given("Сущность напоминания") {
                expectedBirthdayNotification = BirthdayNotification(
                    contactDetailsInfo,
                    true,
                    testTriggerDate
                )
                Mockito.`when`(
                    notificationRepository.setBirthdayReminder(
                        contactDetailsInfo, testTriggerDate
                    )
                ).thenReturn(expectedBirthdayNotification)
            }
            When("Устанавливается напоминание") {
                actualBirthdayNotification =
                    contactDetailsPresenter.setNotification(contactDetailsInfo)
            }
            Then("Напоминание установлено") {
                assertEquals(
                    expectedBirthdayNotification,
                    actualBirthdayNotification
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
            }

            When("Удаляется напоминание") {
                actualBirthdayNotification =
                    contactDetailsPresenter.removeNotification(contactDetailsInfo)
            }
            Then("Напоминание удалено") {
                assertEquals(
                    expectedBirthdayNotification,
                    actualBirthdayNotification
                )
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
            }

            When("Получение статуса напоминания") {
                actualBirthdayNotification =
                    contactDetailsPresenter.getActualStateBirthdayNotification(contactDetailsInfo)
            }
            Then("Статус получен") {
                assertEquals(
                    expectedBirthdayNotification,
                    actualBirthdayNotification
                )
            }
        }
    }
})
