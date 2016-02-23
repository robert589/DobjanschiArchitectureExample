package com.robertlimantoproject.madebygue;

/**
 * Created by user on 23/7/2015.
 */
public class Constants {

    public final static String HOST_ADDRESS = "http://192.168.131.1/Madebygue";

        public final static String LINK_LOGIN_USER = HOST_ADDRESS + "/loginuser.php";

    public final static String LINK_REG_USER = HOST_ADDRESS + "/registeruser.php";

    public final static String LINK_VALIDATION_EMAIL = HOST_ADDRESS + "/resend_email.php";

    public final static String LINK_CSV = HOST_ADDRESS + "/check_status_validation.php";

    public final static String LINK_EDIT_PROFILE = HOST_ADDRESS + "/edit_profile.php";

    public final static String LINK_RETRIEVE_USER_RELATION = HOST_ADDRESS + "/retrieve_user_relation.php";

    public final static String LINK_CHECK_UPDATE = HOST_ADDRESS + "/check_update.php";

    public final static String LINK_UPDATE_CATEGORY =  HOST_ADDRESS + "/update_category.php";

    public final static String LINK_UPDATE_PRODUCT = HOST_ADDRESS + "/update_product.php";

    public final static String LINK_UPDATE_SUBCATEGORY = HOST_ADDRESS + "/update_subcategory.php";

    public final static String LINK_UPDATE_SUBPRODUCT = HOST_ADDRESS + "/update_subproduct.php";

    public class CreateProductPage{
        public final static String SUBCATEGORY_ID = "subcategory_id";
    }

    public class SubcategoryCreatePage{

        public final static String CATEGORY_ID = "category_id";


    }

    public class ResponseObject{
        public final static String USER = "user";

        public final static String USER_RELATION = "user_relation";

        public final static String CATEGORY = "category";

        public final static String SUBCATEGORY = "subcategory";

        public final static String PRODUCT = "product";
    }

    public class LoginPage{

        /**RECEIVER **/
        public final static String RECEIVER = "login_page_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";
        //Communication to PHP
        public final static String EMAIL = "email";

        public final static String PASSWORD = "password";

        /**Communication**/
        public final static String USER = "user";

        /**For text **/
        public final static String EMAIL_REQUIRED_FIELD = "email field required";

        public final static String PASSWORD_REQUIRED_FIELD = "password field required";



    }

    public class RegisterPage{

        /**RECEIVER **/
        public final static String RECEIVER = "register_page_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        //Communication to PHP
        public final static String EMAIL = "email";

        public final static String PASSWORD = "password";

        public final static String NAME = "name";

        public final static String ADDRESS = "address";

        public final static String NO_HP  = "NO_HP";

        /**Communication**/
        public final static String USER = "user_register";

        public final static String EMAIL_SENT = "send_email";

        /**For text**/
        public final static String EMAIL_REQUIRED_FIELD = "Email field cannot be empty";

        public final static String PASSWORD_REQUIRED_FIELD = "Password field cannot be empty";

        public final static String PASSWORD_LENGTH = "Password length must be at least 6 chars";

        public final static String NAME_REQUIRED_FIELD = "Name field cannot be empty";

        public final static String CFRM_PSWN_REQ_FIELD = "Write you password again";

        public final static String PSWN_DOES_NOT_MATCH  = "Password does not match";

        public final static String EMAIL_NOT_VALID = "Email is not valid";


    }

    public class ValidateEmailPage{
        /**RECEIVER **/
        public final static String RECEIVER = "validate_email_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        public final static String EMAIL = "email";

        public final static String SENDING_EMAIL = "sending email";

        public final static String EMAIL_SENT = "email sent";

        public final static String COMMAND = "command";

        public final static String RESEND_EMAIL_COMMAND  = "resend_email_command";

        public final static String CSV_COMMAND  = "csv_command";

    }

    public class MainPage{
        public final static String ARG_PAGE = "page";

        public final static int PAGE_NUMBER = 5;

        /**RECEIVER **/
        public final static String RECEIVER = "main_page_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        //communication
        public final static String EMAIL = "email";



    }

    public class DistroGuePage{
        //communication
        public final static String EMAIL = "email";

        /**RECEIVER **/
        public final static String RECEIVER = "distro_gue_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        //Command
        public final static String COMMAND = "command";

        //user_relation_command
        public final static String UPDATE_USER_RELATION_COMMAND = "uur_command";
    }

    public class IDGuePage{
        public final static  String ADD_YOUR_ADDRESS = "Add your address";

        public final static String ADD_YOUR_HANDPHONE = "Add your handphone number";

        public final static String IS_VALIDATED = "Your account is validated";

        public final static String IS_NOT_VALIDATED = "Your account is not validated";
    }

    public class EditProfilePage{
        /**RECEIVER **/
        public final static String RECEIVER = "edit_profile_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        //Intent Communication
        public final static String USER = "user_update";

        /**Communication to php **/
        public final static String EMAIL = "email";

        public final static String NAME = "name";

        public final static String ADDRESS = "address";

        public final static String NO_HP  = "NO_HP";

        public final static String PASSWORD  = "password";
    }

    public class CreatePage{
        /**RECEIVER **/
        public final static String RECEIVER = "create_page_receiver";

        //RESPONSE
        public final static String RESPONSE = "response";

        //Command
        public final static String COMMAND = "command";

        //user_relation_command
        public final static String CHECK_UPDATE_COMMAND = "cu_command";

        public final static String UPDATE_CATEGORY_COMMAND = "uc_command";

        public final static String  UPDATE_PRODUCT ="up_command";

        public final static String UPDATE_SUBCATEGORY = "us_command";

        public final static String UPDATE_SUBPRODUCT = "sp_command";

        //UPDATE_STATUS CODE
        public final static int CATEGORY = 10;

        public final static int PRODUCT_TYPE = 1;

        //Communication
        public final static String EMAIL = "email";
    }


}
