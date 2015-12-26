LayoutInflater li = LayoutInflater.from(ctx);
View view = li.inflate(R.layout.promptdialog, null);

//get a builder and set the view
AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
builder.setTitle("Prompt");
builder.setView(view);


