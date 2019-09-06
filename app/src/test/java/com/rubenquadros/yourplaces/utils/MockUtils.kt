package com.rubenquadros.yourplaces.utils

import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.*
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.Feature
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.Geocode
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.Geometry
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.SearchPlacesResponse
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.Venue

class MockUtils {

    companion object {
        fun nearbyplacesResponse(): PlacesResponse {
            return mockPlacesResponse()
        }

        fun searchPlacesResponse(): SearchPlacesResponse {
            return mockSearchPlacesResponse()
        }

        private fun mockSearchPlacesResponse(): SearchPlacesResponse {
            val searchPlacesResponse = SearchPlacesResponse()
            searchPlacesResponse.meta = mockSearchMeta()
            searchPlacesResponse.response = mockSearchResponse()
            return searchPlacesResponse
        }

        private fun mockSearchMeta(): com.rubenquadros.yourplaces.data.remote.model.searchplaces.Meta {
            val meta = com.rubenquadros.yourplaces.data.remote.model.searchplaces.Meta()
            meta.code = 200
            meta.requestId = "5d71d5b223bb8e0038be3dd3"
            return meta
        }

        private fun mockSearchResponse(): com.rubenquadros.yourplaces.data.remote.model.searchplaces.Response {
            val response = com.rubenquadros.yourplaces.data.remote.model.searchplaces.Response()
            response.confident = false
            response.geocode = mockGeoCode()
            response.venues = mockVenues()
            return response
        }

        private fun mockVenues(): ArrayList<Venue> {
            val venues = ArrayList<Venue>()

            val venue1 = Venue()
            venue1.id = "4be43b12cf200f47f6a3113c"
            venue1.name = "Anjappar"
            venue1.contact = com.rubenquadros.yourplaces.data.remote.model.searchplaces.Contact()
            venue1.location?.address = "Intermediate Ring Road"
            venue1.location?.crossStreet = "Koramangala"
            venue1.location?.lat = 12.932162461291599
            venue1.location?.lng = 77.62288892012221
            venue1.location?.labeledLatLngs?.get(0)?.label = "display"
            venue1.location?.labeledLatLngs?.get(0)?.lat = 12.932162461291599
            venue1.location?.labeledLatLngs?.get(0)?.lng = 77.62288892012221
            venue1.location?.cc = "IN"
            venue1.location?.city = "Bangalore"
            venue1.location?.state = "Karnātaka"
            venue1.location?.country = "India"
            venue1.location?.formattedAddress = listOf("Intermediate Ring Road (Koramangala)", "Bangalore", "Karnātaka", "India")
            venue1.categories?.get(0)?.id = "4bf58dd8d48988d10f941735"
            venue1.categories?.get(0)?.name = "Indian Restaurant"
            venue1.categories?.get(0)?.pluralName = "Indian Restaurants"
            venue1.categories?.get(0)?.shortName = "Indian"
            venue1.categories?.get(0)?.icon?.prefix = "https://ss3.4sqi.net/img/categories_v2/food/indian_"
            venue1.categories?.get(0)?.icon?.suffix = ".png"
            venue1.categories?.get(0)?.primary = true
            venue1.verified = false
            venue1.stats?.tipCount = 0
            venue1.stats?.checkinsCount = 0
            venue1.stats?.usersCount = 0
            venue1.stats?.visitsCount = 0
            venue1.beenHere?.count = 0
            venue1.beenHere?.lastCheckinExpiredAt = 0
            venue1.beenHere?.marked = false
            venue1.beenHere?.unconfirmedCount = 0
            venue1.hereNow?.count = 0
            venue1.hereNow?.summary = "Nobody here"
            venue1.hereNow?.groups = emptyList()
            venue1.referralId = "v-1567741362"
            venue1.hasPerk = false
            venue1.venueChains = emptyList()

            venues.add(venue1)

            return venues
        }

        private fun mockGeoCode(): Geocode {
            val geocode = Geocode()
            geocode.what = ""
            geocode.where = "koramangala"
            geocode.parents = emptyList()
            geocode.feature = mockFeature()
            return geocode
        }

        private fun mockFeature(): Feature {
            val feature = Feature()
            feature.cc = "IN"
            feature.name = "Koramangala"
            feature.displayName = "Koramangala, Tamil Nādu, India"
            feature.matchedName = "Koramangala, Tamil Nādu, India"
            feature.highlightedName = "<b>Koramangala</b>, Tamil Nādu, India"
            feature.woeType = 7
            feature.id = "geonameid:6695463"
            feature.longId = "72057594044623399"
            feature.geometry = mockGeometry()
            return feature
        }

        private fun mockGeometry(): Geometry {
            val geometry = Geometry()
            geometry.center?.lat = 12.93204
            geometry.center?.lng = 77.6227
            return geometry
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

        fun getPlacesEntity(): ArrayList<PlacesEntity> {
            val mockPlacesEntities = ArrayList<PlacesEntity>()

            val placesEntity1 = PlacesEntity(1, 12.943336934688098, 77.55250151104914,
                                "Barista", "Coffee Shop", "First Floor (JP Nagar), Bangalore, Karnātaka, India")

            mockPlacesEntities.add(placesEntity1)

            val placesEntity2 = PlacesEntity(2, 12.943156242129977, 77.55242314093947,
                                    "Cafe Coffee Day", "Café", "Raghvendra Block (Srinagar), India")

            mockPlacesEntities.add(placesEntity2)

            return mockPlacesEntities
        }
    }
}