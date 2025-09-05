package viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepViewModel : ViewModel() {
    var step = MutableLiveData(1)
}