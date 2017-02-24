# PiZzza <img src="app/src/main/res/mipmap-hdpi/ic_launcher.png" />
Swiggy coding assignment

# NOTE
- hosted JSON has 2 JSON array inside exclude_list 
- {(1,3);(2,10)} = {Crust:CheeseBurst;Size:Small} and {(2,10);(3,22)} = {Size:Small;Sauce:Mustard}
- So, when user selects Crust-CheeseBurst then only Size-small will be hidden but when user selects Size-small then 2 options will be hiddden
  (Crust:CheeseBurst and Sauce:Mustard, both will be hidden) as (2-Size,10-Small) appears at two places above
- I think the question description requires bit amendment as there was no such exclusion list {(1,2);(2,12)}={(Crust,Thick);(Size,Large)} in the hosted JSON file
- See the code to know more, I have added comments at all the necessary places.
 
# Components Used
- Retrofit2
- Dynamically created RadioGroup & RadioButton

# ScreenShots
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/no_selection.jpg?alt=media&token=608960a7-95a8-46f2-955c-d8d2a583f0e9" width = 400>&nbsp;&nbsp;
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/cheese_burst.jpg?alt=media&token=bbebf7a1-785d-4371-a311-614939deedd9" width = 400></br></br>
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/small_size.jpg?alt=media&token=e95d73e1-5db5-40c5-a09b-456b81a4b259" width = 400>&nbsp;&nbsp;
<img src="https://firebasestorage.googleapis.com/v0/b/delhi06-31a81.appspot.com/o/mustard_sauce.jpg?alt=media&token=4631b294-f582-4628-8974-73245b1d054e" width = 400>&nbsp;&nbsp;
