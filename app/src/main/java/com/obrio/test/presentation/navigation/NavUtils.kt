package com.obrio.test.presentation.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController

/**
 * Extension property to check if the NavHostController can navigate back.
 *
 * This property verifies if the current back stack entry is in the `RESUMED` state,
 * meaning the current composable is active and in the foreground. This ensures
 * safe back navigation without issues like navigating back from an inactive or
 * destroyed back stack entry.
 *
 * @return `true` if the current back stack entry is in the `RESUMED` state, `false` otherwise.
 */
val NavHostController.canGoBack : Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

/**
 * Extension function to safely navigate back in a composable-based navigation graph.
 *
 * This function checks if back navigation is possible using the `canGoBack` property.
 * If it is safe to navigate back (i.e., the current composable is active and in the
 * `RESUMED` state), it pops the back stack. Otherwise, it does nothing to avoid
 * navigation issues.
 *
 * Use this function to ensure smooth and error-free back navigation in composable-based
 * navigation when the lifecycle state of the back stack entry needs to be verified.
 */
fun NavHostController.safeNavigateBackComposable() {
    if (canGoBack) {
        popBackStack()
    }
}