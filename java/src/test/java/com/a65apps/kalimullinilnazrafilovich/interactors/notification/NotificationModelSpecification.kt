package com.a65apps.kalimullinilnazrafilovich.interactors.notification

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime
import com.nhaarman.mockitokotlin2.mock
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.util.*
import kotlin.test.assertEquals

const val YEAR_1999 = 1999
const val YEAR_1990 = 1990
const val YEAR_2000 = 2000
const val YEAR_2004 = 2004
const val YEAR_1992 = 1992

const val DAY_OF_MONTH_7 = 7
const val DAY_OF_MONTH_8 = 8
const val DAY_OF_MONTH_9 = 9
const val DAY_OF_MONTH_29 = 29

object NotificationModelSpecification : Spek({
    val currentTime: CurrentTime = mock()
    val birthdayCalendar: BirthdayCalendar = mock()
    val notificationRepository: NotificationRepository = mock()

    val contactShortInfo = ContactShortInfo(
            "id",
            "name",
            "tel"
    )

    lateinit var currentDate: GregorianCalendar
    lateinit var birthdayDate: GregorianCalendar
    lateinit var testTriggerDate: GregorianCalendar
    lateinit var contactDetailsInfo: ContactDetailsInfo

    Feature("Успешное добавление напоминания") {
        lateinit var expectedBirthdayNotification: BirthdayNotification
        lateinit var actualBirthdayNotification: BirthdayNotification

        Scenario("Установить напоминание на следующий год") {
            Given(" Текущий год - 1999(не високосный) 9 сентября") {
                currentDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("Есть контакт с датой рождения 8 сентября") {
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
                contactDetailsInfo = ContactDetailsInfo(
                        contactShortInfo,
                        birthdayDate,
                        "t2",
                        "e1",
                        "e2",
                        "",
                        null
                )
            }
            And("Создания напоминания на 9 сентября 2000 года") {
                testTriggerDate = GregorianCalendar(YEAR_2000, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        true,
                        testTriggerDate
                )

                Mockito.`when`(notificationRepository.setBirthdayReminder(
                        contactDetailsInfo,
                        testTriggerDate
                )).thenReturn(expectedBirthdayNotification)

            }

            When("Пользователь кликает на кнопку напоминания в детальной информации контакта") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )

                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)
            }

            Then("Происходит успешное добавление напоминания на 2000 год 8 сентября") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Успешное удаление напоминания") {
            Given("Текущий год - 1999(не високосный)") {
                currentDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("Есть контакт с датой рождения 8 сентября") {
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
                contactDetailsInfo = ContactDetailsInfo(
                        contactShortInfo,
                        birthdayDate,
                        "t2",
                        "e1",
                        "e2",
                        "",
                        null
                )
            }
            And("Создания напоминания cо статусом работы false") {
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        false,
                        null)
                Mockito.`when`(notificationRepository.removeBirthdayReminder(contactDetailsInfo))
                        .thenReturn(expectedBirthdayNotification)
            }
            When("Пользователь кликает на кнопку напоминания в детальной информации контакта") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.offBirthdayNotification(contactDetailsInfo)
            }

            Then("Происходит успешное удаление напоминания") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Успешное добавление напоминания, ДР еще в текущем году не было") {
            Given("Текущий год - 1999(не високосный) 7 сентября") {
                currentDate = GregorianCalendar(YEAR_1999, Calendar.AUGUST, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("Есть контакт с датой рождения 8 сентября") {
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
                contactDetailsInfo = ContactDetailsInfo(
                        contactShortInfo,
                        birthdayDate,
                        "t2",
                        "e1",
                        "e2",
                        "",
                        null
                )
            }
            And("Создание напоминания") {
                testTriggerDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        true,
                        testTriggerDate
                )

                Mockito.`when`(notificationRepository.setBirthdayReminder(
                        contactDetailsInfo,
                        testTriggerDate
                )).thenReturn(expectedBirthdayNotification)
            }

            When("Пользователь кликает на кнопку напоминания в детальной информации контакта") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)
            }

            Then("Происходит успешное добавление напоминания на 1999 год 8 сентября") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Добавление напоминания для контакта родившегося 29 февраля") {
            Given("Текущий год - 1999(не високосный), следующий 2000(високосный) 7 марта") {
                currentDate = GregorianCalendar(YEAR_1999, Calendar.MARCH, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("Есть контакт с датой рождения 29 февраля") {
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_29)
                contactDetailsInfo = ContactDetailsInfo(
                        contactShortInfo,
                        birthdayDate,
                        "t2",
                        "e1",
                        "e2",
                        "",
                        null
                )
            }
            And("Создание напоминания") {
                testTriggerDate = GregorianCalendar(YEAR_2000, Calendar.SEPTEMBER, DAY_OF_MONTH_29)
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        true,
                        testTriggerDate
                )

                Mockito.`when`(notificationRepository.setBirthdayReminder(
                        contactDetailsInfo,
                        testTriggerDate
                )).thenReturn(expectedBirthdayNotification)
            }

            When("Пользователь кликает на кнопку напоминания в детальной информации контакта") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)
            }

            Then("Происходит успешное добавление напоминания на 2000 год 29 февраля") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Добавление напоминания для контакта родившегося 29 февраля в високосный год") {
            Given("Текущий год - 2000(високосный) 7 марта") {
                currentDate = GregorianCalendar(YEAR_2000, Calendar.MARCH, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("Есть контакт с датой рождения 29 февраля") {
                birthdayDate = GregorianCalendar(YEAR_1992, Calendar.FEBRUARY, DAY_OF_MONTH_29)
                contactDetailsInfo = ContactDetailsInfo(
                        contactShortInfo,
                        birthdayDate,
                        "t2",
                        "e1",
                        "e2",
                        "",
                        null
                )
            }
            And("Создание напоминания") {
                testTriggerDate = GregorianCalendar(YEAR_2004, Calendar.FEBRUARY, DAY_OF_MONTH_29)
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        true,
                        testTriggerDate
                )

                Mockito.`when`(notificationRepository.setBirthdayReminder(
                        contactDetailsInfo,
                        testTriggerDate
                )).thenReturn(expectedBirthdayNotification)
            }

            When("Пользователь кликает на кнопку напоминания в детальной информации контакта") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)
            }

            Then("Происходит успешное добавление напоминания на 2004 год 29 февраля") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }
    }
})