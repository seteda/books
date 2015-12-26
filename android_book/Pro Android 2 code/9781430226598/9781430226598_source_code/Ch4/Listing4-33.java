@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.frame);


    ImageView one = (ImageView)this.findViewById(R.id.oneImgView);
    ImageView two = (ImageView)this.findViewById(R.id.twoImgView);

    one.setOnClickListener(new OnClickListener(){

        @Override
        public void onClick(View view) {
            ImageView two = (ImageView)FramelayoutActivity.this.
findViewById(R.id.twoImgView);

            two.setVisibility(View.VISIBLE);

            view.setVisibility(View.GONE);
        }});

    two.setOnClickListener(new OnClickListener(){

        @Override
        public void onClick(View view) {
            ImageView one = (ImageView)FramelayoutActivity.
this.findViewById(R.id.oneImgView);

            one.setVisibility(View.VISIBLE);

            view.setVisibility(View.GONE);
        }});
}

