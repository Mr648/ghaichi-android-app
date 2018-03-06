//package com.sorinaidea.arayeshgah.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import software.uok.vanet.datahelper.PersianDay;
//import software.uok.vanet.datahelper.PersianMonth;
//
///**
// * Created by Mr648 on 5/8/2017.
// */
//public class JalaliCalendar {
//    private String jalaliWeekDayName = "";
//    private String jalaliMonthName = "";
//
//    private int jalaliDay;
//    private int jalaliMonth;
//    private int jalaliYear;
//
//    public JalaliCalendar(long date) {
//        Date georgianDate = new Date(date);
//        calcSolarCalendar(georgianDate);
//    }
//
//
//    private void calcSolarCalendar(Date georgianDate) {
//
//        int ld;
//
//        int georgianYear = georgianDate.getYear() + 1900;
//        int georgianMonth = georgianDate.getMonth() + 1;
//        int georgianDay = georgianDate.getDate();
//        int weekDay = georgianDate.getDay();
//
//        int[] buf1 = new int[]{
//                0,
//                31,
//                59,
//                90,
//                120,
//                151,
//                181,
//                212,
//                243,
//                273,
//                304,
//                334
//        };
//        int[] buf2 = new int[]{
//                0,
//                31,
//                60,
//                91,
//                121,
//                152,
//                182,
//                213,
//                244,
//                274,
//                305,
//                335
//        };
//
//        if ((georgianYear % 4) != 0) {
//            jalaliDay = buf1[georgianMonth - 1] + georgianDay;
//
//            if (jalaliDay > 79) {
//                jalaliDay = jalaliDay - 79;
//                if (jalaliDay <= 186) {
//                    switch (jalaliDay % 31) {
//                        case 0:
//                            jalaliMonth = jalaliDay / 31;
//                            jalaliDay = 31;
//                            break;
//                        default:
//                            jalaliMonth = (jalaliDay / 31) + 1;
//                            jalaliDay = (jalaliDay % 31);
//                            break;
//                    }
//                    jalaliYear = georgianYear - 621;
//                } else {
//                    jalaliDay = jalaliDay - 186;
//
//                    switch (jalaliDay % 30) {
//                        case 0:
//                            jalaliMonth = (jalaliDay / 30) + 6;
//                            jalaliDay = 30;
//                            break;
//                        default:
//                            jalaliMonth = (jalaliDay / 30) + 7;
//                            jalaliDay = (jalaliDay % 30);
//                            break;
//                    }
//                    jalaliYear = georgianYear - 621;
//                }
//            } else {
//                if ((georgianYear > 1996) && (georgianYear % 4) == 1) {
//                    ld = 11;
//                } else {
//                    ld = 10;
//                }
//                jalaliDay = jalaliDay + ld;
//
//                switch (jalaliDay % 30) {
//                    case 0:
//                        jalaliMonth = (jalaliDay / 30) + 9;
//                        jalaliDay = 30;
//                        break;
//                    default:
//                        jalaliMonth = (jalaliDay / 30) + 10;
//                        jalaliDay = (jalaliDay % 30);
//                        break;
//                }
//                jalaliYear = georgianYear - 622;
//            }
//        } else {
//            jalaliDay = buf2[georgianMonth - 1] + georgianDay;
//
//            if (georgianYear >= 1996) {
//                ld = 79;
//            } else {
//                ld = 80;
//            }
//            if (jalaliDay > ld) {
//                jalaliDay = jalaliDay - ld;
//
//                if (jalaliDay <= 186) {
//                    switch (jalaliDay % 31) {
//                        case 0:
//                            jalaliMonth = (jalaliDay / 31);
//                            jalaliDay = 31;
//                            break;
//                        default:
//                            jalaliMonth = (jalaliDay / 31) + 1;
//                            jalaliDay = (jalaliDay % 31);
//                            break;
//                    }
//                    jalaliYear = georgianYear - 621;
//                } else {
//                    jalaliDay = jalaliDay - 186;
//
//                    switch (jalaliDay % 30) {
//                        case 0:
//                            jalaliMonth = (jalaliDay / 30) + 6;
//                            jalaliDay = 30;
//                            break;
//                        default:
//                            jalaliMonth = (jalaliDay / 30) + 7;
//                            jalaliDay = (jalaliDay % 30);
//                            break;
//                    }
//                    jalaliYear = georgianYear - 621;
//                }
//            } else {
//                jalaliDay = jalaliDay + 10;
//
//                switch (jalaliDay % 30) {
//                    case 0:
//                        jalaliMonth = (jalaliDay / 30) + 9;
//                        jalaliDay = 30;
//                        break;
//                    default:
//                        jalaliMonth = (jalaliDay / 30) + 10;
//                        jalaliDay = (jalaliDay % 30);
//                        break;
//                }
//                jalaliYear = georgianYear - 622;
//            }
//
//        }
//        jalaliMonthName = PersianMonth.values()[jalaliMonth].getPersianName();
//        jalaliWeekDayName = PersianDay.values()[weekDay].getPersianName();
//
//    }
//
//    public int getJalaliDay() {
//        return jalaliDay;
//    }
//
//    public void setJalaliDay(int jalaliDay) {
//        this.jalaliDay = jalaliDay;
//    }
//
//    public int getJalaliMonth() {
//        return jalaliMonth;
//    }
//
//    public void setJalaliMonth(int jalaliMonth) {
//        this.jalaliMonth = jalaliMonth;
//    }
//
//    public String getJalaliMonthName() {
//        return jalaliMonthName;
//    }
//
//
//    public String getJalaliWeekDayName() {
//        return jalaliWeekDayName;
//    }
//
//
//    public int getJalaliYear() {
//        return jalaliYear;
//    }
//
//    public void setJalaliYear(int jalaliYear) {
//        this.jalaliYear = jalaliYear;
//    }
//
//    public static String getJalaliDate(long date) {
//        JalaliCalendar jalaliCalendar = new JalaliCalendar(date);
//        String persianDate = "";
//        persianDate += jalaliCalendar.getJalaliWeekDayName();
//        persianDate += "، ";
//        persianDate += Integer.toString(jalaliCalendar.getJalaliDay());
//        persianDate += " ";
//        persianDate += jalaliCalendar.getJalaliMonthName();
//        persianDate += " ";
//        persianDate += Integer.toString(jalaliCalendar.getJalaliYear());
//        persianDate += "، " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
//        return persianDate;
//    }
//
//}
