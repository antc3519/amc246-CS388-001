package caruso.example.myapplication

class ItemFetcher {
    companion object {
        var itemList = listOf<String>()
        var priceList = listOf<String>()
        var urlList = listOf<String>()
        fun getItems(): MutableList<Item> {
            var items : MutableList<Item> = ArrayList()
            val size = itemList.size
            if (size >= 1) {
                for (i in items.indices) {
                    val item = Item(itemList[i], priceList[i], urlList[i])
                    items.add(item)
                }
                return items
            }
            return items
        }
    }
}