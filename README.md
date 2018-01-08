# Programmeer Project
this document contains general information about this app and the problem it solves.

Problem
-------
There are a lot of different kinds of collectors. They keep their own administration of the items they poses. These administrations are often on paper and it is hard to keep it well organized or to search an item on specifications. Collectors can also not share their collections with other people.

Solution
--------
This app will provide an easy way to administrate and share your own collection in an android app.

![alt text](https://github.com/haantje0/Programmeer-Project/edit/master/README.md)


In the sketches in the PDF 'sketch app' you can find different screens for the app. 
- The first page you see the screen which shows you all your collections.
- If you press the menu icon on the upper left, you open the second page. This shows a menu with options.
- By pressing on a collection you get to the third page. this page shows your collection in a certain order.
- If you press an item in your collection you get the forth screen, which shows the specific details for that item and a photo.

This app will at least include:
-	Creating you own custom collection with self-chosen specifications and photos.
-	Sharing your collections with your Facebook friends.

If possible, this app will include:
-	Adding non-Facebook friends to see other collections.
-	Searching for items through the collections of your friends.

Prerequisites
-------------
Data sources:
- Facebook API (https://developers.facebook.com/docs/android/getting-started)
- Firebase API (https://console.firebase.google.com/)

External components:
- Phone Camera API
- SQLite

Similar Android apps:
- Collections Manager. It lets you create your own collection. You are unable to share your collection and chose your own specifications.
- Collection Manager. It lets you create your own collection with self-chosen specifications. You cannot search through the specifications and you cannot share your collection.

The hardest parts of developing this app will be the implementation of sharing your collection with your Facebook friends and being able to choose which part you want to be public and which part not. Also letting the user chose which specifications he wants to use for the collection will be a challenge.

