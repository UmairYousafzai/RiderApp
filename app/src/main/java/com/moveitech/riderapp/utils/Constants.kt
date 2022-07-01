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

        const val LOGIN = "api/SaleOrder/RiderLogin"
        const val SALE_ORDER_BY_RIDER = "api/SaleOrder/SalesOrderByRider"
        const val SAVE_ORDER_TRACKING = "api/SaleOrder/SaveOrderTracking"
        const val SAVE_ORDER_STATUS = "api/SaleOrder/OrderStatusChangeByRider"

        /****************************   BTN KEY **********************/

        const val LOCATION_BTN=10
        const val RIDER_PENDING_BTN=0
        const val RIDER_HOLD_BTN=1
        const val RIDER_DISPATCH_BTN=2
        const val RIDER_COMPlETE_BTN=3
        const val RIDER_CANCEL_BTN=4
        const val RIDER_CLOSE_BTN=5
    }
}