Spinner s2 = (Spinner) findViewById(R.id.spinner2);

adapter = ArrayAdapter.createFromResource(this, 
R.array.planets,android.R.layout.simple_spinner_item);

adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

s2.setAdapter(adapter);


<string-array name="planets">
    <item>Mercury</item>
    <item>Venus</item>
    <item>Earth</item>
    <item>Mars</item>
    <item>Jupiter</item>
    <item>Saturn</item>
    <item>Uranus</item>
    <item>Neptune</item>
</string-array>

