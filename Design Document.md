# Design Document
This document gives a description of what the app will look like and how the program works in the background. This includes which classes will be used, how databases are ordered and how APIs are used.

Advanced sketches
-----------------
1.	Login screen (facebook logo)
2.	Collections overview (list of collections, add button)
3.	Side menu (own collection button, friends button)
4.	Add collections (give title, set extra specs, share?)
5.	Extra specs popup (title, input)
6.	Items overview (list of items(title and picture), add button, search function)
7.	Item specs (image, title, extra specs, change option)
8.	Add item (input for all specs)

https://github.com/haantje0/Programmeer-Project/blob/master/sketch%20design%20document.png

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

