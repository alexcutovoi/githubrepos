package com.alexcutovoi.githubrepos.main.ui

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alexcutovoi.githubrepos.R
import com.alexcutovoi.githubrepos.common.InternetManager
import com.alexcutovoi.githubrepos.databinding.ActivityMainBinding
import com.alexcutovoi.githubrepos.main.data.local.GithubDatabase
import com.alexcutovoi.githubrepos.main.data.remote.HttpClientImpl
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepositoryImpl
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepositoryLocalImpl
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.alexcutovoi.githubrepos.main.domain.usecases.GetPageUseCase
import com.alexcutovoi.githubrepos.main.domain.usecases.GetRepositoriesUseCase
import com.alexcutovoi.githubrepos.main.domain.usecases.SavePageUseCase
import com.alexcutovoi.githubrepos.main.domain.usecases.SaveRepositoriesUseCase

class MainActivity : AppCompatActivity(), BaseView {
    private lateinit var activityLoginBinding: ActivityMainBinding
    private lateinit var gitHubViewModel: GithubViewModel
    private val repositoriesAdapter: GithubRepositoriesAdapter by lazy {
        GithubRepositoriesAdapter(this, arrayListOf(), activityLoginBinding.repositories) {
            getData()
        }
    }

    private val internetManager: InternetManager by lazy {
        InternetManager(this.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        setupConfig()
    }

    private fun setupConfig() {
        activityLoginBinding.repositories.adapter = repositoriesAdapter

        gitHubViewModel = ViewModelProvider(this, GithubViewModel.GithubViewModelFactory(
            GetRepositoriesUseCase(GithubRepositoryImpl(HttpClientImpl())),
            GetRepositoriesUseCase(GithubRepositoryLocalImpl(GithubDatabase.getDb().getGithubDao())),
            SaveRepositoriesUseCase(GithubRepositoryLocalImpl(GithubDatabase.getDb().getGithubDao())),
            SavePageUseCase(GithubRepositoryLocalImpl(GithubDatabase.getDb().getGithubDao())),
            GetPageUseCase(GithubRepositoryLocalImpl(GithubDatabase.getDb().getGithubDao())))
        ).get(GithubViewModel::class.java)

        gitHubViewModel.repositoriesLiveData.observe(this) { viewState ->
            when(viewState){
                is ViewState.Success -> {
                    hideLoading()
                    repositoriesAdapter.addItems((viewState.data as Repositories).repositoryList)
                }
                is ViewState.IsLoading -> {
                    showLoading()
                    hideReloadButton()
                }
                is ViewState.Error -> {
                    hideLoading()
                    hideReloadButton()
                    Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_LONG).show()
                }
                else -> {
                    hideLoading()
                    hideReloadButton()
                    Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_LONG).show()
                }
            }

        }

        activityLoginBinding.tryAgainButton.setOnClickListener   {
            getData()
        }
    }

    override fun showLoading() {
        activityLoginBinding.loading.loadingScreenView.visibility = View.VISIBLE
    }
    override fun hideLoading() {
        activityLoginBinding.loading.loadingScreenView.visibility = View.GONE
    }

    private fun showReloadButton(){
        activityLoginBinding.tryAgainButton.visibility = View.VISIBLE
    }

    private fun hideReloadButton(){
        activityLoginBinding.tryAgainButton.visibility = View.GONE
    }

    private fun getData() {
        if(internetManager.checkInternet()) {
            gitHubViewModel.getRepositories()
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
            hideLoading()
            showReloadButton()
        }
    }
}