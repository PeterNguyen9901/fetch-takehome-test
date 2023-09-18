package com.fetchhiring;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ViewModel class responsible for fetching, filtering, and sorting items for display.
 */
public class ItemViewModel extends ViewModel {
    // LiveData for items to observe changes
    public MutableLiveData<List<ItemModel>> itemsLiveData = new MutableLiveData<>();
    public MutableLiveData<LoadingState> loadingState = new MutableLiveData<>();

    // This function fetches items from the server.
    private APIService apiService;

    //Constructor for dependency injection
    public ItemViewModel(APIService apiService) {
        this.apiService = apiService;
    }

    //default constructor
    public ItemViewModel() {

        this.apiService = APIClient.getInstance().getRetrofit().create(APIService.class);
    }




    public void fetchItems() {
        loadingState.postValue(LoadingState.LOADING);

        Call<List<ItemModel>> call = apiService.fetchItems();

        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemModel> fetchedItems = response.body();

                    if (fetchedItems != null) {
                        // filter name is not null and sort the numbers
                        List<ItemModel> filteredItems = fetchedItems.stream()
                                .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                                .collect(Collectors.toList());
                        filteredItems.sort((item1, item2) -> {
                            if (item1.getListId() == item2.getListId()) {
                                try {
                                    int nameInt1 = extractInteger(item1.getName());
                                    int nameInt2 = extractInteger(item2.getName());

                                    return Integer.compare(nameInt1, nameInt2);
                                } catch (NumberFormatException e) {
                                    return item1.getName().compareTo(item2.getName());
                                }
                            }
                            return Integer.compare(item1.getListId(), item2.getListId());
                        });

                        List<ItemModel> filteredAndSortedItems = new ArrayList<>();
                        int currentListId = 0;
                        for (ItemModel item : filteredItems) {
                            if (item.getListId() != currentListId) {
                                currentListId = item.getListId();
                                filteredAndSortedItems.add(ItemModel.createHeader(currentListId));
                            }
                            filteredAndSortedItems.add(item);
                        }
                        itemsLiveData.postValue(filteredAndSortedItems);
                        loadingState.postValue(LoadingState.LOADED);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                itemsLiveData.postValue(null); // Indicate an error by posting null.
                loadingState.postValue(LoadingState.ERROR);
            }
        });
    }

    /**
     * Extracts an integer from the string that contains the item and number
     * */
    public int extractInteger(String str) {
        String num = str.replaceAll("\\D", "");
        return Integer.parseInt(num);
    }

    //to represent loading states for the ViewModel
    public enum LoadingState {
        LOADING,
        LOADED,
        ERROR
    }

}

