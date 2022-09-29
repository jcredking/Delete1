# AndroidR_Delete
Delete  Utils for android R and R+ version for delete media files 


* Step 1. Add the JitPack repository to your build file 


~~~ gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ~~~
  
  * Step 2. Add the dependency
  
  ~~~ gradle
	dependencies {
	        implementation 'com.github.jcredking:AndroidR_Delete:1.0.1'
	}
  
  ~~~


 * How to use

 
 ~~~ java
        
        //NOTE :- THIS NEW INSTANCE DECLARE IN ACTIVITY 
 
         DeleteUtilsR deleteUtilsR = new DeleteUtilsR(this);
~~~

* Public Methods 

~~~ java

//for delete Audio

  deleteUtilsR.deleteAudio("YOUR_FILE_PATH", new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                        
                        //Perform after delete things 
                        
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                         
                        }
                    });
         
 //for delete Image

deleteUtilsR.deleteImage(("YOUR_FILE_PATH", new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                  
                        }
                    });

//for delete video

deleteUtilsR.deleteVideo(("YOUR_FILE_PATH", new DeleteCallBack() {
                        @Override
                        public void onDeleted() {
                            Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                     
                        }
                    });
               
~~~

* For Delete Multiple Files

~~~ java
    
    //call below method same as above 
    
    deleteUtilsR.deleteAudiosList(ArrayList<String> paths, DeleteCallBack deleteCallBack);
    deleteUtilsR.deleteImagesList(ArrayList<String> paths, DeleteCallBack deleteCallBack); 
    deleteUtilsR.deleteVideosList(ArrayList<String> paths, DeleteCallBack deleteCallBack);
    
    ~~~




