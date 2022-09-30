package bf.digital.mobile.imlookingfor.activities

import com.google.firebase.database.DatabaseReference

interface LookingAppCallback {

    /**
     * Function that sign out the current user logged in and navigate to the StartupActivity and
     * Finish the current activity.
     */
    fun onSignout();

    /**
     * Function that returns the current user ID.
     */
    fun onGetUserId(): String;

    /**
     * Function that returns the models database.
     */
    fun getModelDatabase(): DatabaseReference;

    /**
     * Function that returns the companies database.
     */
    fun getCompanyDatabase(): DatabaseReference;

    /**
     * Function that redirect to the swipe fragment after the user completed his profile.
     */
    fun profileComplete();

    /**
     * Function that allow us to put a picture.
     */
    fun startActivityForPhoto();

    /**
     * Function that returns the role of the user, could be model or company
     */
    fun getRoleUser(): String;


}