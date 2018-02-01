# Programmeer Project
this document contains general information about this app and the problem it solves.

Here is a video that demonstrates the product (in Dutch):
https://youtu.be/GS1d5yU3j6k

[![BCH compliance](https://bettercodehub.com/edge/badge/haantje0/Programmeer-Project?branch=master)](https://bettercodehub.com/)

## Problem
There are a lot of different kinds of collectors. They keep their own administration of the items they poses. These administrations are often on paper and it is hard to keep it well organized or to search an item on specifications. Collectors can also not share their collections with other people.

## Solution
This app will provide an easy way to administrate and share your own collection in an android app.
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/app%20sketch%201.jpeg" width="200"/>
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/app%20sketch%202.jpeg" width="200"/>
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/app%20sketch%203.jpeg" width="200"/>
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/app%20sketch%204.jpeg" width="200"/>

In the screenshots you can see different screens for the app. 
- The first page you see the screen which shows you all your collections.
- If you press the friend icon on the upper right, you open the second page. This shows a list with your Facebook friends.
- By pressing on a collection you get to the third page. this page shows your collection in a certain order.
- If you press an item in your collection you get the forth screen, which shows the specific details for that item and a photo.

This app will at least include:
-	Creating you own custom collection with self-chosen specifications and photos.
-	Sharing your collections with your Facebook friends.

If possible, this app will include:
-	Adding non-Facebook friends to see other collections.
-	Searching for items through the collections of your friends.

## Prerequisites
Data sources:
- Facebook API (https://developers.facebook.com/docs/android/getting-started)
- Firebase API (https://console.firebase.google.com/)

External components:
- Phone Camera API

Code sources:
- The idea for the Facebook friendlist came from https://github.com/sallySalem/Facebook-Friends-list. This is an open source.

Design sources:
- The Icons in the app are from https://material.io/icons/. This is an open source for Android icons.

Similar Android apps:
- Collections Manager. It lets you create your own collection. You are unable to share your collection and chose your own specifications.
- Collection Manager. It lets you create your own collection with self-chosen specifications. You cannot search through the specifications and you cannot share your collection.
