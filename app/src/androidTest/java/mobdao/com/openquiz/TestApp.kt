package mobdao.com.openquiz

import android.app.Application

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 */
class TestApp : Application()