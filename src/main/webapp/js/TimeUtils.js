/**
 * Created by 1 on 2017/1/17.
 */
/**
 * 时间工具
 * @type {{showWeekFirstDay: timeUtil.showWeekFirstDay, showWeekLastDay: timeUtil.showWeekLastDay, showMonthFirstDay: timeUtil.showMonthFirstDay, showMonthLastDay: timeUtil.showMonthLastDay}}
 */
var timeUtil = {
    showWeekFirstDay: function () {
        var Nowdate = new Date();
        var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
        return WeekFirstDay.toLocaleDateString().replaceAll("/", "-");
    },
    showWeekLastDay: function () {
        var Nowdate = new Date();
        var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
        var WeekLastDay = new Date((WeekFirstDay / 1000 + 6 * 86400) * 1000);
        return WeekLastDay.toLocaleDateString().replaceAll("/", "-");
    },
    showMonthFirstDay: function () {
        var Nowdate=new Date();
        var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1);
        return MonthFirstDay.toLocaleDateString().replaceAll("/", "-");
    },
    showMonthLastDay: function () {
        var Nowdate=new Date();
        var MonthNextFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth()+1,1);
        var MonthLastDay=new Date(MonthNextFirstDay-86400000);
        return MonthLastDay.toLocaleDateString().replaceAll("/", "-");
    },
    showYearFirstDay: function () {
        var Nowdate = new Date();
        var YearFirstDay = new Date(Nowdate.getFullYear(), 0, 1);
        return YearFirstDay.toLocaleDateString().replaceAll("/", "-");
    },
    showYearLastDay: function () {
        var Nowdate = new Date();
        var YearLastDay = new Date(Nowdate.getFullYear(), 11,31);
        return YearLastDay.toLocaleDateString().replaceAll("/", "-");
    }
}