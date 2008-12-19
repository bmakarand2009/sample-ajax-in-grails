class DatePickerService {
    static transactional = false
    
    def calendarSplit = { cal ->
        def ampm = cal.get(Calendar.AM_PM) == Calendar.AM ? 'AM' : 'PM'
        // make 0 hours 12 for logical reading
        def hour = cal.get(Calendar.HOUR)
        if (hour == 0) hour = 12 
        return [
            year:cal.get(Calendar.YEAR),
            month:cal.get(Calendar.MONTH)+1,
            day:cal.get(Calendar.DAY_OF_MONTH),
            hour:hour,  
            minute:cal.get(Calendar.MINUTE),
            second:cal.get(Calendar.SECOND),
            ampm:ampm
        ]
    }
}