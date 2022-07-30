package com.moveitech.riderapp.utils

class Constants {

    companion object
    {
        const val Location_NOTIFICATION_CHANNEL_ID=" location_notification_channel_id"
        const val ACTION_START_LOCATION_SERVICE = "start_location_service"
        const val ACTION_STOP_LOCATION_SERVICE = "stop_location_service"
        const val EXTRA_LOCATION_TYPE = "location_type"
        const val EXTRA_TRACKING_ID = "tracking_id"
        const val LOCATION_PERMISSION_CODE = 2

        /****************************   API END POINTS **********************/

        const val LOGIN = "api/rider/RiderLogin"
        const val SALE_ORDER_BY_RIDER = "api/SaleOrder/SalesOrderByRider"
        const val TRACKING_BY_SALE_ORDER = "api/SaleOrder/TracingRiderBySaleOrder"
        const val SAVE_ORDER_TRACKING = "api/SaleOrder/SaveOrderTracking"
        const val SAVE_ORDER_STATUS = "api/SaleOrder/OrderStatusChangeByRider"
        const val RIDERS = "api/rider/RiderData"
        const val SAVE_RIDERS = "api/rider/SaveRider"
        const val RIDER_BY_CODE = "api/rider/RiderByCode"
        const val RIDER_ROLE = "api/rider/RiderRolesData"
        const val SAVE_RIDER_ROLE = "api/rider/SaveRiderRoles"

        /****************************   BTN KEY **********************/

        const val LOCATION_BTN=10
        const val TRACK_LOCATION_BTN=6
        const val RIDER_PENDING_BTN=0
        const val RIDER_HOLD_BTN=1
        const val RIDER_DISPATCH_BTN=2
        const val RIDER_COMPlETE_BTN=3
        const val RIDER_CANCEL_BTN=4
        const val RIDER_CLOSE_BTN=5
        const val ADD_RIDER_BTN=7
        const val EDIT_RIDER_BTN=8
        const val SAVE_RIDER_BTN=9
        const val SAVE_ROLE_BTN=11
        const val ADD_ROLE_BTN=12
        const val EDIT_ROLE_BTN=13
        const val CANCEL_BTN=14
    }
}