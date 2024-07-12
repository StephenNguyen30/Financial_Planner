import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.financialplanner.databinding.ContentFragmentBinding
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel

class ContentFragment : BaseFragment<ContentFragmentBinding>(ContentFragmentBinding::inflate) {
    override val viewModel: BaseViewModel by viewModels()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initRcvContent()
    }

    private fun initRcvContent() {
    }
}