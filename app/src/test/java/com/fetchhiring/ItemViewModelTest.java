package com.fetchhiring;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

public class ItemViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    APIService apiService;

    @Mock
    Call<List<ItemModel>> call;

    private ItemViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        viewModel = new ItemViewModel(apiService);
    }

    @Test
    public void testFetchItemsSuccess() {
        List<ItemModel> items = Arrays.asList(
                new ItemModel(1, "Item 1", 1),
                new ItemModel(2, "Item 2", 2)
        );

        when(apiService.fetchItems()).thenReturn(call);
        doAnswer(invocation -> {
            Callback<List<ItemModel>> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(items));
            return null;
        }).when(call).enqueue(any(Callback.class));

        viewModel.fetchItems();

        assertNotNull(viewModel.itemsLiveData.getValue());
        assertTrue(viewModel.itemsLiveData.getValue().containsAll(items));
        assertEquals(ItemViewModel.LoadingState.LOADED, viewModel.loadingState.getValue());
    }

    @Test
    public void testFetchItemsFailure() {
        when(apiService.fetchItems()).thenReturn(call);
        doAnswer(invocation -> {
            Callback<List<ItemModel>> callback = invocation.getArgument(0);
            callback.onFailure(call, new Throwable("Error fetching items"));
            return null;
        }).when(call).enqueue(any(Callback.class));

        viewModel.fetchItems();

        assertNull(viewModel.itemsLiveData.getValue());
        assertEquals(ItemViewModel.LoadingState.ERROR, viewModel.loadingState.getValue());
    }

    @Test
    public void testExtractInteger() {
        String input = "Item 123";
        int result = viewModel.extractInteger(input);
        assertEquals(123, result);
    }
}
