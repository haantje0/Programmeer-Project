# Report
-----
## Application description
This app is made for collectors of all sorts. It provides an easy way to administrate your collections and share them via Facebook.
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/app%20sketch%203.jpeg" width="200"/>

## Technical design
In this flowchart you can see how the app works and how the activities are linked.
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/flow%20chart.jpg" width="800"/>

#### Activities
1. LoginActivity
    -  This Activity is the MainActivity. The user can log in via Facebook and go to his collections (CollectionsActivity).
    -  There is a standard Facebook button which is used to log in and log out.
    -  When someone is already logged in, there will be a welcome text and a button to continue with the current user  (CollectionsActivity).
    -  When there is no-one logged in, you can only go further by login in to Facebook.
    -  When the user uses the logout button from the toolbar, it returns to this activity and the user can log out.
    -  After leaving this activity, it is removed from the back stash. You can only return by pressing the back button from the toolbar.

2. CollectionsActivity
    - This activity shows the collections that a user has. This activity is the home page for a user.
    - The collections are loaded from Firebase via the DatabaseManager and dependent on where you came from (FriendsActivity or not).
    - When a collection is clicked, it will bring you to that collection (CollectionViewActivity).
    - When a collection is longclicked, it will ask the user if he wants to delete the collection or not via a alert dialog. 
    - There is a floating action button with a plus sign. When this is clicked, it will bring the user to the AddCollectionActivity.
    - When this activity is opened by clicking on a friend in the FriendsActivity, it will show the collections of the chosen friend. This will also create a different toolbar, make the add button disappear and disable the LongClickListener.

3. CollectionViewActivity
    - This activity shows the items in the clicked collection (title, description and image).
    - It loads the items from Firebase via the DatabaseManager. It gets the right collection via an intent.
    - When an item is clicked, it will bring you to that item. 
    - When an item is longclicked, it will ask the user if he wants to delete the item or not via an alert dialog.
    - There is a floating action button with a plus sign. When this is clicked, it will bring the user to the AddItemActivity.
    - When this activity is opened for a friend's collection, it will show the items of that collection. This will also create a different toolbar, make the add button disappear and disable the LongClickListener.

4. ItemViewActivity
    - This activity shows the given item and its specifications (including picture).
    - All the information displayed in this Activity was already taken from the database in the previous activity and given via an intent. This activity only displays it.

5. AddCollectionActivity
    - This activity lets the user add new collections. 
    - The title of the collection and extra specifications can be added to the database via the DatabaseManager.
    - When a user wants to add extra specifications, an alert dialog will open and the user can chose the name of the specification and the input type (text or number).
    - New specifications can be deleted by clicking the red cross.
    - Once the save button is clicked, the name of the collection and the chosen specifications will be saved in the database. You will return to the CollectionsActivity.

6. AddItemActivity
    - This activity lets the user add new items to a collection. All the specifications can be filled in in numerous edittexts.
    - When a collection has extra specs enabled, these extra specs will also have edittexts and the user can add these specs.    
    - The extra specifications are loaded from the database via the DatabaseManager.
    - The phone camera is linked to this activity. The user can add a photo of the item by clicking on the imageview. This will start the camera API.
    - Once the save button is clicked, all specifications and the photo will be saved in the database via the DatabaseManager. You will return to the CollectionViewActivity.

7. FriendsActivity
    - This activity displays the Facebook friends that also use the app.
    - The friends are loaded from the Facebook Graph API and placed into a listview.
    - When a friend is clicked, it will bring you to that friend's collections (CollectionsActivity).

#### Classes
- DatabaseManager
    - This Helper class handles all calls with the Firebase database.
    - It takes information from the database and stores information to it.
    - When information is asked from the database, this helper will also put the information in the right view.
    - Before using the helper it always has to be initialized via a constructor. This will make sure that the right collections are taken when you try to view the collections of a friend.

- Specs
    - This class contains information from an item in a collection:
        - Name
        - Description
        - Image
        - Extra specs (Hashmap, key: name, value: input type).

- FriendItem
    - This class contains information from a Facebook friend:
        - User ID
        - User name
        - Picture URL

- ItemViewAdapter
    - This class puts items with title, description and picture in a listview.
    - It uses the itemslayout.xml to do this.

- FriendsAdapter
    - This class puts FriendItems in a listview with picture and name.
    - It uses the friendslayout.xml to do this.
    - It downloads the profile picture asynchronous.


#### Toolbar
- menu_main.xml
    - This menu contains two buttons:
        - friends, which brings you to FriendsActivity.
        - logout, which brings you to LoginActivity.
    - This menu is inflated when you view your own collection in CollectionsActivity or CollectionViewActivity.

- menu_friends.xml
    - This menu contains two buttons:
        - home, which brings you to CollectionsActivity.
        - logout, which brings you to LoginActivity.
    - This menu is inflated when you are in the FriendsActivity or when you navigate through a friend's collection (CollectionsActivity or CollectionViewActivity).

#### Database Structure
Here you can see an example of how the database is structured:
<img src="https://github.com/haantje0/Programmeer-Project/blob/master/doc/database%20branching.jpg" width="800"/>

The Firebase database is mainly branched into the different users (Facebook IDs):
1. A user is branched into all of its collections.
2. Each collection consists of the items in the collections (4) and the extra specifications for that collection (3).
3. in the extra specifications branch, the specifications are saved as a hashmap (key= title, value= input type).
4. In the collection branch, the different items are saved.
5. Each item has its name, description, image and extraSpecs (6).
6. In the last branch the extra specs specific for this collections (same as in branch 3) are saved for the item.

## Development challenges
#### Facebook API
The setting up of the Facebook API was a challenge and has cost me a few days. I never worked with hashkeys and gradle scripts before so I had no idea what I was doing. Eventually the app did not run on my emulator anymore but it worked on my phone. But also on my phone it gave a lot of trouble, since the Facebook button did not work all the time. I looked at it with some TAs but they also could not fix it. Eventually I stayed logged in most of the time to bypass the login button. This problem has not been fixed at all, and is probably a problem of my phone.

#### Images on Firebase
When you want to save images to firebase it suggests doing that with Firebase Storage. This would be another component to install to the app and would cost the necessary time. Eventually I found a way (with the help of a team mate) to convert the bitmap into a string which is savable in Firebase.

#### Dynamic specifications
To implement the dynamic specifications you have to be able to add and delete views (edittexts and textviews). It was quite a challenge to view the self-added specifications and save them to the database. Once the views could be dynamically added, the challenge was in how to save the names of the specifications and the value type. At first I tried to save them in different string arrays and save each string to the database. This was a hard job and it turned out to be much easier when you save the data in a hashmap, since a hashmap can be easily saved to Firebase.

At first I was not sure how to implement the extra specifications. I firstly thought that it should be able to change the specifications for each item individually, but I thought it would be more user-friendly to set the extra specifications specifically for a collection and not for items individually. It took some time for me to figure out a way to save this information in a good way in Firebase. Eventually I came up with the structure I explained in the section above, which is very clear and powerful.

#### Android Studio Versions
At a certain point my Android Studio asked if I would update its version. When I did that, my whole app did not build anymore and I got stuck for a few days. Eventually, with the help of multiple TAs, the problem was solved. It turned out that the version of the Android Studio did not work properly with my Facebook SDK.

#### Viewing friends collections
At first I tried to start the database of a user with its Firebase authentication ID. But when I tried to view collections of Facebook friends, it was hard to couple the Facebook ID with the Firebase ID. I tried to put extra information in the database as user information, but it turned out to be a lot of work. Eventually I solved it by changing the beginning of the database from Firebase ID to Facebook ID. In that way you can easily navigate through branches of your friends since the Facebook ID is loaded via the Facebook Graph API.

#### Facebook Profile pictures
I tried to view the Facebook profile pictures in the FriendsView. When you download the information from all Facebook friends who are also using the app, the Facebook Graph API does only return the names and Facebook IDs. When you ask the API to return all Facebook friends (also the ones that are not using the app) it does also return profile picture URLs. I tried to solve this by using standard URLs with the Facebook ID in it, but it did not work out in time. 
