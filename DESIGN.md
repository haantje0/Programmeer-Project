# Design Document
This document gives a description of what the app will look like and how the program works in the background. This includes which classes will be used, how databases are ordered and how APIs are used.

Advanced sketches
-----------------
![alt text](https://github.com/haantje0/Programmeer-Project/blob/master/doc/sketch%20design%20document%201.2.png)

1.	Login screen. Here you can login on facebook. You have to log in, in order to access the app.
2.	Collections overview. Here you can see your collections. These collections are loaded from firebase. You can go to the menu (3), add a new collection (5) or see a collection (6).
3.	Side menu. You can go to your own collections (2) or to your friens list (4). 
4.	Friends list. These are your facebook friends who are also using this app. These names are extracted form the Facebook API. Clicking a friend will bring you to his collection (2). The same screen is used, this is done by changing the FacebookID, and taking a different branch in firebase. You can view the collections and items but you cannot change these collections, becouse your FacebookID does not match the FacebookID of the collection.
5.	Add collections. When you want to add a new collection you have to give a title and you can add extra specs to the collection. You do this via a popup where you can name the spec and sellect what type of input you have to give. Saving it creates a new branch in Firebase, with the new specs already in it.
6.	Items overview. This screen loads all the items in your collection (photo and title) from Firebase. You can click the add button to add an item (7) or click an item to see its specs (8). When you long click an item, it wil popup a delete screen.
7.	Add item. Here you can add a new item to your collection. When you add a photo, your camera will open up. The specs are the standard specs (photo, title, date, amount, description) and your own chosen specs.
8.	Item specs. Here you can see the specs of the selected item. You can click edit to edit them (7).

Modules, Classes and Functions 
------------------------------
Class: User
-	String name
-	String facebookID
-	Constructor(name, facebookID)
-	getName()
-	getFacebookID()

Modules:
- FireBaseManager
    - createUser()
    - createCollection()
    - createItem()
    - changeItem()
- DataBaseManager
- FacebookManager
    - getFacebookID()
    - getFriendList()
- CameraManager
- CollectionsAdapter
- FriendsAdapter
- ItemsAdapter
- ItemViewAdapter


APIs and plugins
----------------
- Facebook API. From this API we will get userIDs to verify which user is logged in and which collections can be changed (only the users own collection). This ID is saved in he firebase database.
- Firebase API. With this API we make an online database which saves users collections. Users are seperated by their names and FacebookIDs. 
- Phone Camera plugin. With this plugin we allow the user to add photos to their items.

Data sources
------------
- Facebook API (https://developers.facebook.com/docs/android/getting-started)
    - It will only be used to logg in and to save FacebookIDs.
- Firebase API (https://console.firebase.google.com/)
    - It is used to save the data in a database.
- Camera API (https://developer.android.com/guide/topics/media/camera.html)
    - It starts the camera and it will return a bitmap image.

Database
--------

The database (Firebase) will concist of differtent users, which are saved as their FacebookID. In there you can find the users real name and their collections. These collections branch into the different items and a branch to state the extra specs for this collection. Each item has the standard specs and the extra specs saved in that particular collection.

database example:
- FacebookID (string)
    - Name (string)
    - Collections
        - Collection
            - ExtraSpecs
                - ExtraSpec1 (string)
                - SpecType1 (type)
                - ExtraSpec2 (string)
                - SpecType2 (type)
            - ItemID (int)
                - Title (string)
                - Image (bitmap)
                - Date (date)
                - Description (string)
                - Amount (int)
                - ExtraSpec1 (type)
                - ExtraSpec2 (type)
            - ItemID (int)
                - Title (string)
                - Image (bitmap)
                - Date (date)
                - Description (string)
                - Amount (int)
                - ExtraSpec1 (type)
                - ExtraSpec2 (type)
        - Collection
            - ExtraSpecs
                - ExtraSpec3 (string)
                - SpecType3 (type)
            - ItemID
                - Title (string)
                - Image (bitmap)
                - Date (date)
                - Description (string)
                - Amount (int)
                - ExtraSpec3 (type)
- FacebookID (string)
    - ....
