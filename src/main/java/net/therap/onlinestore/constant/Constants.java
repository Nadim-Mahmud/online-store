package net.therap.onlinestore.constant;

/**
 * @author nadimmahmud
 * @since 3/6/23
 */
public interface Constants {

    int ITEM_PER_PAGE = 8;
    String REDIRECT = "redirect:/";
    String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
    String CELL_NO_PATTERN = "01([0-9]){9,9}";
    String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    String ACTIVE_USER = "activeUser";
    String USER = "user";
    String USER_LIST = "userList";
    String USER_TYPE_LIST = "userTypeList";
    String SHOPKEEPER = "shopkeeper";
    String DELIVERYMAN = "deliveryMan";
    String CUSTOMER = "customer";
    String CATEGORY = "category";
    String CATEGORY_LIST = "categoryList";
    String CATEGORY_ID = "categoryId";
    String ITEM = "item";
    String ITEM_LIST = "itemList";
    String TAG = "tag";
    String TAG_LIST = "tagList";
    String TAG_ID = "tagId";
    String READY_ORDER = "readyOrder";
    String DELIVERY_LIST = "deliveryList";
    String ORDER = "order";
    String ORDER_LIST = "orderList";
    String ORDER_FORM = "orderForm";
    String ORDER_ITEM = "orderItem";
    String ORDER_ITEM_LIST = "orderItemList";
    String ALL_TAG_LIST = "allTagList";
    String AVAILABILITY_LIST = "availabilityList";
    String UPDATE_PAGE = "updatePage";
    String DETAILS_PAGE = "detailsPage";
    String NAV_ITEM = "navItem";
    String USER_TYPE = "userType";
    String CREDENTIALS = "credentials";
    String PASSWORD = "password";
    String ADDRESS = "address";
    String PROFILE_UPDATE_PAGE = "profileUpdatePage";
    String LOGIN_PAGE = "loginPage";
    String INVALID_LOGIN = "invalidLogin";
    String SUCCESS = "success";
    String FAILED = "failed";
    String PAGE_START_VALUE = "pageStartValue";
    String EMPTY_LIST = "emptyList";
    String BUTTON = "button";
    String HOME_URL = "/";
    String ERROR_CODE = "errorCode";
    String BASE_URL = "/*";
    String ADMIN_BASE_URL = "admin/*";
    String CUSTOMER_BASE_URL = "customer/*";
    String SHOPKEEPER_BASE_URL = "shopkeeper/*";
    String DELIVERY_BASE_URL = "delivery/*";
}
