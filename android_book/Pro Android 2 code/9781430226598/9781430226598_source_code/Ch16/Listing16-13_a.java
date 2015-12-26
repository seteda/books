    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        mapView = (MapView)findViewById(R.id.mapview);

        ClickReceiver clickRecvr = new ClickReceiver(this);
        mapView.getOverlays().add(clickRecvr);
    }

