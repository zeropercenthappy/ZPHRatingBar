# ZPHRatingBar
Slimple Android RatingBar

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.zeropercenthappy:ZPHRatingBar:1.0.2'
	}

Sample:

    <com.zeropercenthappy.ratingbarlibrary.ZPHRatingBar
        android:id="@+id/rb"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:itemClickAble="true"
        app:itemCountNumber="5"
        app:itemHeight="40dp"
        app:itemSelected="@mipmap/ic_launcher"
        app:itemSelectedNumber="0"
        app:itemSpacing="10dp"
        app:itemUnSelected="@mipmap/ic_launcher_round"
        app:itemWidth="40dp" />

Method:

  void setOnRatingClickCallback(OnRatingClickCallback onRatingClickCallback)
  
  int getItemCountNumber()
  
  void setItemCountNumber(int itemCountNumber)
  
  int getItemSelectedNumber()
  
  void setItemSelectedNumber(int itemSelectedNumber)
  
