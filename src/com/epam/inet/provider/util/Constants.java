package com.epam.inet.provider.util;

/**
 * Created by Hedgehog on 01.06.2016.
 */
public final class Constants {

    private Constants() {
    }

    /**
     *  Constants from Command tier
     */
    //  Add Tariff Command
    public static final String ATTRIBUTE_TARIFF_TYPE = "tariffTypes";
    public static final String ATTRIBUTE_TARIFFS = "tariffs";
    public static final String ATTRIBUTE_TARIFF = "tariff";
    public static final String PARAMETER_SUBMIT = "submit";
    public static final String ATTRIBUTE_REGULAR = "regular";
    public static final String PATH_MANAGER_PAGE = "path.page.admin.manager";
    public static final String PATH_MANAGER_ADD_TARIFF = "path.page.admin.add_tariff";
    public static final String MESSAGE_DB_CREATE_SUCCESS = "info.db.create_success";
    public static final String MESSAGE_INVALID_DATA = "add_tariff.invalid_form_data";
    public static final String MESSAGE_INVALID_EXCEPTION = "add_tariff.invalid_form_data";
    public static final String MESSAGE_INVALID_UPDATE_DATA = "update.tariff.invalid.form.data";

    // Delete command
    public static final String ID = "id";
    public static final String MESSAGE_DB_DELETE_SUCCESS = "info.db.delete_success";
    public static final String PATH_ADMIN_MANAGER_PAGE = "path.page.admin.manager";
    public static final String MESSAGE_INVALID_PARAMETER = "error.invalid_parameter";
    public static final String MESSAGE_DB_NO_SUCH_RECORD = "error.db.no_such_record";
    public static final String PARAMETER_NOTIFICATION = "deleteNotification";

    //  Orders command
    public static final String PARAMETER_PAID = "paid";
    public static final String ATTRIBUTE_ORDERS = "orders";
    public static final String PATH_ADMIN_ORDERS_PAGE = "path.page.admin.orders";

    //  Update Tariff command
    public static final String PATH_ADMIN_UPDATE_TARIFF = "path.page.admin.update_tariff";
    public static final String PARAMETER_TARIFF = "tariffs";

    // Account command
    public static final String PATH_CLIENT_ACCOUNT_PAGE = "path.page.client.account";

    //  Order command
    public static final String ATTRIBUTE_AMOUNT = "amount";
    public static final String ATTRIBUTE_CONFIRM = "confirm";
    public static final String PATH_CLIENT_COMPLETE = "path.page.client.complete";
    public static final String PATH_CLIENT_CLIENT_ORDER = "path.page.client.order";

    //  Empty command
    public static final String PATH_MAIN_PAGE = "path.page.main";

    //  Login command
    public static final String PARAMETER_LOGIN = "login";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_USER = "user";
    public static final String PATH_LOGIN_PAGE = "path.page.login";
    public static final String LOG_MSG_LOGIN_SESSION_CREATED = "logger.message.command.session.created";
    public static final String PARAMETER_LAST_ENTERED_LOGIN = "lastEnteredLogin";
    public static final String PARAMETER_ERROR_LOGIN = "errorLoginOrPass";
    public static final String MSG_ERROR_LOGIN = "jsp.message.error.login";
    public static final String LOG_MSG_INCORRECT_LOGIN = "logger.message.login.command.incorrect.login.or.password";
    public static final String ATTRIBUT_SUCCESS_AUTH = "info.auth.success";

    // ViewTariff command
    public static final String PATH_TARIFFS_PAGE = "path.page.tariffs";

    //Service tier
    public static final String IS_REGULAR = "is_regular";
    //  Controller tier
    public static final String PATH_ERROR_500_PAGE = "path.error500";

    // Dao tier
    public final static String USER_ID = "id";
    public final static String USER_LOGIN = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_ROLENAME = "rolename";
    public static final String USER_NOT_FOUND = "User not found in the system";
    public static final String TARIFF_ID = "id";
    public static final String TARIFF_DETAILS = "details";
    public static final String TARIFF_HOT = "hot";
    public static final String TARIFF_NAME = "tariffname";
    public static final String TARIFF_PRICE = "price";
    public static final String TARIFF_AMOUNT = "amount";
    public static final String TARIFF_REGULAR_DISCOUNT = "regularDiscount";
    public static final String TARIFF_TYPE = "type";
    public static final String ORDER_ID = "id";
    public static final String ORDER_AMOUNT = "amount";
    public static final String ORDER_DATE = "order_date";
    public static final String ORDER_PAID = "paid";
    public static final String NO_CONNECTION = "Unable to establish connection with database";
    public static final String ENTITY_WAS_NOT_FOUND = "No entity with such id";
    public static final String INVALID_DATA = "Null or invalid parameter(s)";
    public static final String NO_ROWS_AFFECTED = "No rows affected";
    public static final String LOGGER_MSG_DAO_ERROR_FETCH_ALLUSERS = "Couldn't fetch list of users out of DB";
    public static final String DAO_ERROR_FETCH_ALLUSERS = "Dao method couldn't perform sql query for finding all users";
    public static final String DAO_ERROR_FETCH_USER_BY_LOGIN = "Dao method couldn't perform sql query for finding user by login";
    public static final String DAO_ERROR_CREATE_USER = "Dao method couldn't perform sql query for creating new user";
    public static final String DAO_ERROR_DELETE_USER_BY_ID = "Dao method couldn't perform sql query for deleting user by id";
    public static final String DAO_ERROR_DELETE_TARIFF_BY_ID = "Dao method couldn't perform sql query for deleting tariff plan by id";
    public static final String DAO_ERROR_TARIFF_NO_RECORD = "There is no such record of tariff plan in Database";
    public static final String DAO_ERROR_CREATE_TARIFF_PLAN_RECORD = "Dao method couldn't perform sql query for creating tariff plan";
    public static final String DAO_ERROR_UPDATE_TARIFF_PLAN_RECORD = "Dao method couldn't perform sql query for updating tariff plan";
    public static final String DAO_ERROR_UPDATE_PAID_FIELD = "Dao method couldn't perform sql query for updating paid field within order";
    public static final String DAO_ERROR_CREATE_NEW_ORDER = "Dao method couldn't perform sql query for creating new order";
    public static final String DAO_ERROR_FETCH_ALL_ORDER_BY_USER = "Dao method couldn't perform sql query for fetching all order by user";
    public static final String DAO_ERROR_FETCH_ALL_ORDERS = "Dao method couldn't perform sql query for fetching all orders";
    public static final String SERVICE_ERROR_ORDERS = "Service tier couldn't perform creating new order function";
    public static final String LOGGER_SERVICE_ORDER_ERROR = "Invalid data from Dao tier. ServiceOrder couldn't perform";
    public static final String LOG_MSG_SERVICE_ERROR = "Invalid data's come from DAO. Service couldn't perform";
    public static final String ERROR_CLOSING_STATEMENT = "Error during closing Prepared Statement object";
    public static final String ERROR_CLOSING_RESULT_SET = "Error during closing Result Set object";
    public static final String LOG_MSG_USER_DAO_INIT = "logger.message.user.dao.init";
    public static final String LOG_MSG_TARIFF_DAO_INIT = "logger.message.tariff.dao.init";
    public static final String LOG_MSG_ORDER_DAO_INIT = "logger.message.order.dao.init";

    //  Service tier
    public static final String ERROR_FIND_LOGIN_PASS = "Couldn't fetch user with such login or password from DB";
    public static final String LOG_MSG_INVALID_LOGIN_PASS = "Couldn't fetch user with such login or password";
    public static final String LOG_MSG_CLIENT_SERVICE = "Client service has been initialized";


    //  Filters tier
    public static final String PATH_ERROR_404_PAGE = "path.error404";

    //  Registration command
    public static final String EMPTY_STRING = "";
    public static final String PATTERN_LOGIN = "pattern.login";
    public static final String PATTERN_PASSWORD = "pattern.password";

    public static final String PARAMETER_REGISTRATION_LOGIN = "login";
    public static final String PARAMETER_REGISTRATION_PASSWORD = "password";
    public static final String PARAMETER_REGISTRATION_PASSWORDREPEAT = "passwordRepeat";
    public static final String PARAMETER_INCORRECT_LOGIN = "incorrectLogin";
    public static final String PARAMETER_CONFIRM_PASSWORD = "confirmPassword";
    public static final String PARAMETER_NONCONFIRMED_PASSWORD = "nonConfirmPass";
    public static final String PARAMETER_INCORRECT_PASSWORD = "incorrectPass";
    public static final String PARAMETER_USER_CREATED = "userCreated";
    public static final String PARAMETER_USER_EXIST = "userExists";

    public static final String MSG_INCORRECT_LOGIN = "registration.message.incorrect.login";
    public static final String MSG_NONCONFIRMED_PASSWORD = "registration.message.nonconfirmed.pass";
    public static final String MSG_INCORRECT_PASSWORD = "registration.message.incorrect.pass";
    public static final String MSG_USER_CREATED = "registration.message.user.created";
    public static final String MSG_USER_EXIST = "message.user.exists";

    public static final String LOGGER_MSG_USER_FIND_BY_LOGIN = "logger.message.user.find.by.login";
    public static final String LOGGER_MSG_USER_FIND_BY_LOGIN_NOT_FOUND = "logger.message.user.find.by.login.not.found";
    public static final String LOGGER_MSG_COMMAND_USER_REGISTERED = "logger.message.user.registered";
    public static final String LOGGER_MSG_REGISTRATION_COMMAND_ERROR = "logger.message.registration.command.error";
    public static final String PATH_ERROR_PAGE = "path.error500";
    public static final String PATH_PAGE_REGISTRATION = "path.page.client.registration";

    //  Login command
    public static final String PARAMETER_AUTHENTIFICATION_FAIL = "authFail";
    public static final String MSG_INCORRECT_LOGIN_PASSWORD = "auth.message.incorrect.login";

    //  Service tier
    public static final String ERROR_SEREVICE_AUTHENTIFICATION = "Error authentification user on service tier";
    public static final String LOG_MSG_TARIFSERVICE_INIT = "logger.message.tariff.service.init";
    public static final String LOG_MSG_TARIFF_SERVICE = "logger.message.tariff.error";
    public static final String LOG_MSG_AUTH_SERVICE_INIT = "logger.message.authentication.service.init";
    public static final String LOG_MSG_ORDER_SERVICE_INIT = "logger.message.order.service.init";
    public static final String EXC_MSG_WRONG_DAO_DATA = "service.exception.message";


    //  Logger messages
    public static final String LOG_MSG_SUCCESS_AUTH = "Successful authentication by login: ";
    public static final String LOG_MSG_FAIL_AUTH = "Authentication fail by login: ";
    public static final String LOG_MSG_LOGGED_OUT = "Logged out: ";
    public static final String LOG_MSG_CONNECTION_TAKEN = " taken from connection pool";
    public static final String LOG_MSG_CONNECTION_RETRIEVE_INVALID = "Couldn't retrieve a connection from pool";
    public static final String LOG_MSG_CONNECTION_RETURNED = " returned to connection pool";
    public static final String LOG_MSG_CONNECTION_REMAIN_IN_POOL = " connection(s) remain in the pool.";
    public static final String LOG_MSG_CLOSE_CONNECTION_ERROR = "Couldn't close connection: ";
    public static final String LOG_MSG_POOL_SHUT_DOWN = "Connection pool is shut down";
    public static final String LOG_MSG_COMMAND_WORKS = " : command works ";
    public static final String LOG_MSG_ERROR_INIT_CONTROLLER = "Error during Controller initialization";
    public static final String LOG_MSG_ERROR_REQUEST = "Error request processing by the Controller";
    public static final String LOG_MSG_SERVICE_ERROR_LOGIN = "Login Service couldn't perform login for a user";
    public static final String LOG_MSG_WRONG_DATE_CONFIG = "Wrong date configuration";
    public static final String LOG_MSG_BYPASS_FILTER = "Someone tried bypass filter to access content file directories";

    public static final String SQL_QUERY = "SELECT client_info.is_regular FROM user  LEFT JOIN client_info ON user.id = client_info.user_id WHERE user.id = ?";
    public static final String SQL_ERROR = "Sql error";
    public static final String NO_CONNECTION_MESSAGE = "Unable to get connection";

}

