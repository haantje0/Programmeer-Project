# Design Document
This document gives a description of what the app will look like and how the program works in the background. This includes which classes will be used, how databases are ordered and how APIs are used.

Advanced sketches
-----------------
![alt text](https://github.com/haantje0/Programmeer-Project/blob/master/sketch%20design%20document%201.1.png)

1.	Login screen. Here you can login on facebook. You have to log in, in order to access the app.
2.	Collections overview. Here you can see your collections. You can go to the menu (3), add a new collection (5) or see a collection (6). These collections are loaded from firebase.
3.	Side menu. You can go to your own collections (2) or to your friens list (4). 
4.	Friends list. These are your facebook friends who are also using this app. Clicking a friend will bring you to his collection (2). The same screen is used, this is done by changing the FacebookID, and taking a different branch in firebase.
5.	Add collections. When you want to add a new collection you have to give a title and you can add extra specs to the collection. You do this via a popup where you can name the spec and sellect what type of input you have to give.
6.	Items overview. This screen loads all the items in your collection (photo and title). You can click the add button to add an item (7) or click an item to see its specs (8). When you long click an item, it wil popup a delete screen.
7.	Add item. Here you can add a new item to your collection. When you add a photo, your camera will open up. The specs are the standard specs (photo, title, date, amount, description) and your own chosen specs.
8.	Item specs. Here you can see the specs of the selected item. You can click edit to edit them (7).

Modules, Classes and Functions 
------------------------------
Class: User
-	String name
-	String FacebookID
-	Constructor
-	getName()
-	getFacebookID()

Class: Item
-	Int itemID
-	Bitmap image
-	String title
-	Date date
-	Int amount
-	String description
-	… (own added specifications)
-	Constructor
-	getImage()
-	getTitle()
-	getDate()
-	getDescription()
-	get…()

APIs and plugins
-------------------
Facebook API
Firebase API
Phone Camera plugin

Data sources
----------------


Database
---------

