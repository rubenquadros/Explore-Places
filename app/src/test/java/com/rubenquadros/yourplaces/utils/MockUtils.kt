package com.rubenquadros.yourplaces.utils

import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.*

class MockUtils {

    companion object {
        fun nearbyplacesResponse(): PlacesResponse {
            return mockPlacesResponse()
        }

        private fun mockPlacesResponse() : PlacesResponse {
            val placesResponse = PlacesResponse()
            placesResponse.meta = mockMeta()
            placesResponse.response = mockResponse()
            return placesResponse
        }

        private fun mockMeta(): Meta {
            val meta = Meta()
            meta.code = 200
            meta.requestId = "5d6be152a4b51b002c4b6533"
            return meta
        }

        private fun mockResponse(): Response {
            val response = Response()
            response.headerLocation = "Bangalore"
            response.headerFullLocation = "Bangalore"
            response.headerLocationGranularity = "city"
            response.totalResults = 9
            response.suggestedBounds = mockSuggestedBounds()
            response.suggestedFilters = mockSuggestedFilters()
            response.warning = mockWarning()
            response.groups = mockGroups()
            return response
        }

        private fun mockGroups(): List<Group> {
            val groups: ArrayList<Group> = ArrayList()

            val group1 = Group()
            group1.name = "recommended"
            group1.type = "Recommended Places"
            group1.items = mockItems()

            groups.add(group1)

            return groups
        }

        private fun mockItems(): List<Item> {
            val items: ArrayList<Item> = ArrayList()

            val item1= Item()
            item1.referralId = "e-0-4c2636bddb5195215a5a2c3a-0"
            item1.reasons?.count = 0
            item1.reasons?.items?.get(0)?.summary = "This spot is popular"
            item1.reasons?.items?.get(0)?.type = "general"
            item1.reasons?.items?.get(0)?.reasonName = "globalInteractionReason"
            item1.venue?.id = "4c2636bddb5195215a5a2c3a"
            item1.venue?.name = "Barista"
            item1.venue?.contact = Contact()
            item1.venue?.location?.address = "First Floor"
            item1.venue?.location?.crossStreet = "JP Nagar"
            item1.venue?.location?.lat = 12.943336934688098
            item1.venue?.location?.lng = 77.55250151104914
            item1.venue?.location?.labeledLatLngs?.get(0)?.label = "display"
            item1.venue?.location?.labeledLatLngs?.get(0)?.lat = 12.943336934688098
            item1.venue?.location?.labeledLatLngs?.get(0)?.lng = 77.55250151104914
            item1.venue?.location?.distance = 789
            item1.venue?.location?.cc = "IN"
            item1.venue?.location?.city = "Bangalore"
            item1.venue?.location?.state = "Karnātaka"
            item1.venue?.location?.country = "India"
            item1.venue?.location?.formattedAddress = listOf("First Floor (JP Nagar)", "Bangalore", "Karnātaka", "India")
            item1.venue?.categories?.get(0)?.id = "4bf58dd8d48988d1e0931735"
            item1.venue?.categories?.get(0)?.name = "Coffee Shop"
            item1.venue?.categories?.get(0)?.shortName = "Coffee Shop"
            item1.venue?.categories?.get(0)?.pluralName = "Coffee Shops"
            item1.venue?.categories?.get(0)?.primary = true
            item1.venue?.categories?.get(0)?.icon?.prefix = "https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_"
            item1.venue?.categories?.get(0)?.icon?.suffix = ".png"
            item1.venue?.verified = true
            item1.venue?.stats?.checkinsCount = 0
            item1.venue?.stats?.tipCount = 0
            item1.venue?.stats?.usersCount = 0
            item1.venue?.stats?.visitsCount = 0
            item1.venue?.beenHere?.count = 0
            item1.venue?.beenHere?.lastCheckinExpiredAt = 0
            item1.venue?.beenHere?.unconfirmedCount = 0
            item1.venue?.beenHere?.marked = false
            item1.venue?.photos?.count = 0
            item1.venue?.photos?.groups = emptyList()
            item1.venue?.hereNow?.count = 0
            item1.venue?.hereNow?.groups = emptyList()
            item1.venue?.hereNow?.summary = "Nobody here"

            items.add(item1)

            return items
        }

        private fun mockWarning(): Warning {
            val warning = Warning()
            warning.text = "There aren't a lot of results near you. Try something more general, reset your filters, or expand the search area."
            return warning
        }

        private fun mockSuggestedFilters(): SuggestedFilters {
            val suggestedFilters = SuggestedFilters()
            suggestedFilters.header = "Tap to show:"
            suggestedFilters.filters = mockFilters()
            return suggestedFilters
        }

        private fun mockFilters(): List<Filter> {
            val filters: ArrayList<Filter> = ArrayList()

            val filter1 = Filter()
            filter1.name = "Open now"
            filter1.key = "openNow"

            filters.add(filter1)

            return filters
        }

        private fun mockSuggestedBounds(): SuggestedBounds {
            val suggestedBounds = SuggestedBounds()
            suggestedBounds.ne = mockNe()
            suggestedBounds.sw = mockSw()
            return suggestedBounds
        }

        private fun mockNe(): Ne {
            val ne = Ne()
            ne.lat = 12.95900000900000
            ne.lng = 77.55921764657094
            return ne
        }

        private fun mockSw(): Sw {
            val sw = Sw()
            sw.lat = 12.940999990999991
            sw.lng = 77.54078235342905
            return sw
        }
    }
}