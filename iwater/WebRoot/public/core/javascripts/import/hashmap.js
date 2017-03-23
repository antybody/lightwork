function HashMap(){ 
/**Map**/ 
var size=0; 
/****/ 
var entry=new Object(); 
/**Map**/ 
this.put=function(key,value){ 
if(!this.containsKey(key)){ 
size++; 
entry[key]=value; 
} 
} 
/**Map**/ 
this.get=function(key){ 
return this.containsKey(key) ? entry[key] : null; 
} 
/**Mapɾ��remove����**/ 
this.remove=function(key){ 
if(this.containsKey(key) && ( delete entry[key] )){ 
size--; 
} 
} 
/**Key**/ 
this.containsKey= function (key){ 
return (key in entry); 
} 
/**Value**/ 
this.containsValue=function(value){ 
for(var prop in entry) 
{ 
if(entry[prop]==value){ 
return true; 
} 
} 
return false; 
} 
/**Value**/ 
this.values=function(){ 
var values=new Array(); 
for(var prop in entry) 
{ 
values.push(entry[prop]); 
} 
return values; 
} 
/**Key**/ 
this.keys=function(){ 
var keys=new Array(); 
for(var prop in entry) 
{ 
keys.push(prop); 
} 
return keys; 
} 
/**Map size**/ 
this.size=function(){ 
return size; 
} 
/**Map**/ 
this.clear=function(){ 
size=0; 
entry=new Object(); 
} 
} 
