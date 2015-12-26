protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.datetime);

    DatePicker dp = (DatePicker)this.findViewById(R.id.datePicker);
    dp.init(2008, 11, 10, null);

    TimePicker tp = (TimePicker)this.findViewById(R.id.timePicker);
    tp.setIs24HourView(true);
    tp.setCurrentHour(new Integer(10));
    tp.setCurrentMinute(new Integer(10));
}

