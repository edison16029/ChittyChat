# ChittyChat
A Chat Application using Firebase Database

This application is developed as a practice based on a Youtube Tutorial.
Link : https://www.youtube.com/playlist?list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8

The Application Contains Following Takeaways :
1) Firebase Authentication
2) TabLayout and ViewPager with Fragments
3) Firebase Storage to store pictures

The Application has following drawbacks:
1) Poor Firebase Datastructure which stores the entire Chats in a single parent "Chats" (Poor Scalability) .
2) Online/Offline changed by the App. Hence, unpredictable behavior due to network errors.
3) Notification, Password Reset, Unread Chat Count , Displaying Last Message are not added. (Mainly because a better data 
structure would make these steps easier and perform faster).
4) Sending Media Files like Pictures, Audio etc.
