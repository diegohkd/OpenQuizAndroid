package mobdao.com.openquiz.data.utils.wrappers.googleauthprovider

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider as wrappedInstance

class GoogleAuthProviderImpl : GoogleAuthProvider {
    override fun getCredential(id: String?): AuthCredential =
        wrappedInstance.getCredential(id, null)
}
