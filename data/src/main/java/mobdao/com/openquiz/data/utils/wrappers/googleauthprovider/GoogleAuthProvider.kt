package mobdao.com.openquiz.data.utils.wrappers.googleauthprovider

import com.google.firebase.auth.AuthCredential

interface GoogleAuthProvider {
    fun getCredential(id: String?): AuthCredential
}