package qqbot.entity.github

data class SearchResult(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<ItemsBean>
) {

    data class ItemsBean(
        val id: Int,
        val name: String,
        val full_name: String,
        val html_url: String,
        val description: String,
        val stargazers_count: Int,
        val watchers_count: Int,
        val language: String?,
        val has_issues: Boolean,
        val has_projects: Boolean,
        val has_downloads: Boolean,
        val has_wiki: Boolean,
        val has_pages: Boolean,
        val forks_count: Int,
        var mirror_url: Any?,
        val archived: Boolean,
        val disabled: Boolean,
        val open_issues_count: Int,
        val open_issues: Int,
        val score: Int
    ) {

        data class OwnerBean(
            val login: String,
            val id: Int,
            val node_id: String,
            val avatar_url: String,
            val gravatar_id: String,
            val url: String,
            val html_url: String,
            val followers_url: String,
            val following_url: String,
            val gists_url: String,
            val starred_url: String,
            val subscriptions_url: String,
            val organizations_url: String,
            val repos_url: String,
            val events_url: String,
            val received_events_url: String,
            val type: String,
            val site_admin: Boolean
        )

        data class LicenseBean(
            val key: String?,
            val name: String?,
            val spdx_id: String?,
            val url: String?,
            val node_id: String?
        )

        data class PermissionsBean(
            val admin: Boolean,
            val push: Boolean,
            val pull: Boolean
        )
    }
}