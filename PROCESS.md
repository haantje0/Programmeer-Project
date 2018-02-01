# week 2

## day 1
- Today I struggled a lot with connecting the app with facebook. It should be nearly done by now. I hope I can finish it tomorow.

## day 2
- Hash Key problem solved. Now I can login with my laptop(emulator) and my phone into facebook. I will have to figure out how to make the app available for other devices to login in a later stadium. For now I can test the app in a developer environment.
- A part of the firebase code which adds a new item to a collection is implemented. 
- I tried to make a new branch in firebase for a new facebook user, but it does not work yet.

## day 3
- The facebook login is working now. There is a problem with the connection with firebase, which makes it sometimes load infinitly long, and sometimes it works fine. I will try to find a solution for that later.
- I can save data in the firebase database. This works withouth the dynamic specs.

## day 4
- The app can now store data to the database and also extract data and put it in views. The hardest part in this was to make sure the app reads the right data and gave that data to the next screen when you click a collection of item.
- It is possible to delete collections or items via a longclick, which is secured via a popup.

## day 5
- I converged images to strings to be able to save them in firebase.
- I made the layout better looking and more userfriendly.
- I started with the dynamic specs.

# week 3

## day 1
- The dynamic specs can be saved to firebase. But it is not easy to load them back. This is because I used ArrayLists.

## day 4
- I updated my android studio and the app did not work anymore. I did not fix the but this day.

## day 5
- I tried to fix the but all day. The app was finally back and running at the end of the day.

## day 6
- I completed the dinamic specs. 
- I changed the extra specs to a Hashmap instead of an ArrayList, which made it easier to load from and to firebase.

## day 7
- I made a friendlist with facebook friends who also use the app. These friends do not show profile pictures. If I load all friends from the user, they do have pictures. I'm going to sort this out another time.
- back navigation is now working a lot better in the app.

# week 4

## day 1
- I changed the authentication mechanism and changed its back navigation.
- The authentication is now working a lot better and the app finally realy sees who is loged in. I needed this to finish the view for the collections of friends.

## day 2
- Finshed the view of friends collections. I had to change the database from starting with the firebase ID to starting with the Facebook ID. In that way you can easily view friends collections in the normal collection activitys.

## day 3
- Worked trough all the TODO's that came out of the code review and fixed some bugs.
- I commented to all the files.
- I tried to add Facebook Profile pictures to the friends activity, but it did not work out.

## day 4
- Today I worked on the report and product video
