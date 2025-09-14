package viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepViewModel : ViewModel() {
    // TODO: Reset this to 1
    var step = MutableLiveData(3)
}