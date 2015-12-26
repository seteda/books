function Person() {
   var age = 40; //init value
   this.setAge = function(howold) { age = howold };
   this.firstname = "First";
   this.lastname = "Last";
}

var me = new Person();
me.firstname = "aaaa";
me.lastname = "bbbb";
me.setAge(25);

//the following will be wrong
me.age=44;
