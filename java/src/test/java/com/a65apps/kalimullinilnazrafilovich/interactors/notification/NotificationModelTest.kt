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


object NotificationModelTest : Spek({
    val YEAR_1999 = 1999
    val YEAR_1990 = 1990
    val YEAR_2000 = 2000
    val YEAR_2004 = 2004
    val YEAR_1992 = 1992

    val DAY_OF_MONTH_7 = 7
    val DAY_OF_MONTH_8 = 8
    val DAY_OF_MONTH_9 = 9
    val DAY_OF_MONTH_29 = 29

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

    Feature("Тест логики установки нужной даты дня рождения") {
        lateinit var expectedBirthdayNotification: BirthdayNotification
        lateinit var actualBirthdayNotification: BirthdayNotification

        Scenario("Установить напоминание на следующий год") {
            Given("Сегодня 8 сентября 2000 года") {
                currentDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("День рождения контакта 9 Сентября 1999 года") {
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
            }
            And("Создание контакта с днем рождения 9-го сентября"){
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
            And("Создания напоминания на 9 сентября 2000 года"){
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

            When("Устанавливается напоминание") {
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )

                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)!!
            }

            Then("Напоминание успешно установлено на 2000 год на 8-е сентября") {
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Удаление существующего напоминания"){
            Given("Сегодня 9-е сентября 1990 года"){
                currentDate = GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("День рождения контакта 8-го сентября 1990 года"){
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
            }
            And("Создание контакта с днем рождения 8-го сентября"){
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
            And("Создания напоминания cо статусом работы false"){
                expectedBirthdayNotification = BirthdayNotification(
                        contactDetailsInfo,
                        false,
                        null)
                Mockito.`when`(notificationRepository.removeBirthdayReminder(contactDetailsInfo))
                        .thenReturn(expectedBirthdayNotification)
            }
            When("Удаляется напоминание"){
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.offBirthdayNotification(contactDetailsInfo)!!
            }

            Then("Происходит успешное удаление напоминания"){
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Установить напоминание на день рождения, которого не было в текущем году"){
            Given("Сегодня 7-е августа 1999 года"){
                currentDate = GregorianCalendar(YEAR_1999, Calendar.AUGUST, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("День рождения контакта 8-го сентября 1990 года"){
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8)
            }
            And("Создание контакта"){
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
            And("Создания напоминания"){
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

            When("Устанавливается напоминание"){
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)!!
            }

            Then("Напоминание успешно установлено на 8-е сентября 1999 года"){
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Установление напоминания для контакта родившегося 29-го февраля"){
            Given("Сегодня 7-е марта 1999 года"){
                currentDate = GregorianCalendar(YEAR_1999, Calendar.MARCH, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("День рождения контакта 29-го февраля 1992 года"){
                birthdayDate = GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_29)
            }
            And("Создание контакта"){
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
            And("Создания напоминания"){
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

            When("Устанавливается напоминание"){
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)!!
            }

            Then("Напоминание успешно установлено на 29-е сентября 2000 года"){
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }

        Scenario("Установить напоминание для контакта родившегося 29-го февраля в високосный год"){
            Given("Сегодня 7-е марта 2000 года"){
                currentDate = GregorianCalendar(YEAR_2000, Calendar.MARCH, DAY_OF_MONTH_7)
                Mockito.`when`(currentTime.now()).thenReturn(currentDate.time.time)
                Mockito.`when`(birthdayCalendar.birthdayCalendar).thenReturn(currentDate)
            }
            And("День рождения контакта 29-го февраля 1992 года"){
                birthdayDate = GregorianCalendar(YEAR_1992, Calendar.FEBRUARY, DAY_OF_MONTH_29)
            }
            And("Создание контакта"){
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
            And("Создания напоминания"){
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

            When("Устанавливается напоминание"){
                val notificationInteractor = NotificationModel(
                        notificationRepository,
                        currentTime,
                        birthdayCalendar
                )
                actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contactDetailsInfo)!!
            }

            Then("Напоминание успешно установлено на 29-е февраля 2004 года"){
                assertEquals(expectedBirthdayNotification, actualBirthdayNotification)
            }
        }
    }
})