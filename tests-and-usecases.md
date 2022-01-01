# Design Doc:
A design doc drawn up based on the designs located in the `./UMLDiagrams` folder of this project

## Use Cases:
|  Who                    |   Use ID  | User Action                                                        |
|-------------------------|-----------|--------------------------------------------------------------------|
| Anonymous               |     1     | Signs Up -> User account is created for this user                  |
| Anonymous               |     2     | Signs In -> User session is set to signed in user                  | 
| Anonymous/Customer/Admin|     3     | Add Item -> Item is added to this user sessions basket             |
| Anonymous/Customer/Admin|     4     | Remove Item -> Item is removed from the basket                     |
| Customer/Admin          |     5     | Sign Out -> users session is invalided                             |
| Customer/Admin          |     6     | Check Out -> assuming success an Invoice for that order is created |
| Anonymous               |     7     | Failed log in -> Erorr is printed to the screen                    |
| Deactivated             |     8     | Log in -> Can't because account is deactivated                     |
| Deactivated             |     9     | Contact Site Admin for account reactivation                        |
| Admin                   |    10     | Make Item -> Item is added to the item Repo                        |
| Admin                   |    11     | Update Item -> Item Details are updated                            |
| Admin                   |    12     | Delete Item -> Item is delete assuming it's foreign key isn't used |
| Admin                   |    13     | View Accounts -> View all user accounts                            |
| Admin                   |    14     | View Orders -> View all orders                                     |
| Admin                   |    15     | Manage Orders -> Manage all System Orders                          |
| Admin                   |    16     | Manage Users -> Manage all system Users                            |
| Admin/Customer          |    17     | View My Account -> Shows their account                             |
| Admin/Customer          |    18     | View My Orders -> Shows their order details                        |
| Admin/Customer          |    19     | Manage My Account -> Manager their account                         |
| Bank                    |    20     | Transfer Money -> (Not Implemented)                                |
| Anonymous/Customer/Admin|    21     | Search Item -> Items with that name or Tag are returned            |

## Test Methods: 
The methods I used to test this application include Junit test modules for the Java code and manual Tests for the HTML/JSP frontend areas of the app manually testing functionaitly and security. Trying things like accessing pages I shouldn't have access to. Doing actions I'm not allowed to with the current user and manually making sure it works as expected. Below is a set of the features I tested manually

#### Admin:
- [X] Create item (Item is created) (Success)
- [X] Delete item (Item is deleted) (Success)
- [X] Update Item (Item is Updated) (Success)
- [X] Manage accounts (Account is Updated) (Success)
- [X] Manage Orders (Order is updated) (Success)

#### Customers and above
- [X] Check out items (Success)
- [X] Check my Orders (Success)
- [X] Sign Out (Success)
- [X] Manage my account (Success)
- [X] Access Admin page (Fails But should Fail)

### Anonymous and above 
- [X] Search items (Success)
- [X] Add item to basket (Success)
- [X] Remove item from Basket (Success)

### Anonymous Only
- [X] Sign in (Success) 
- [X] Create Account (success)
- [X] Create duplicate account (Fails But it should fail)
- [X] Checkout (Fails But should Fail)

