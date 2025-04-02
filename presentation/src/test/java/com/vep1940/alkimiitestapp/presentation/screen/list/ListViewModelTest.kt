package com.vep1940.alkimiitestapp.presentation.screen.list

import app.cash.turbine.test
import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.domain.usecase.AddCharacterToFavsUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.GetFavouriteCharactersUseCase
import com.vep1940.alkimiitestapp.domain.usecase.RemoveCharacterFromFavsUseCase
import com.vep1940.alkimiitestapp.lang.failure
import com.vep1940.alkimiitestapp.lang.success
import com.vep1940.alkimiitestapp.presentation.model.GenderDisplay
import com.vep1940.alkimiitestapp.presentation.screen.list.model.ListScreenState
import com.vep1940.alkimiitestapp.presentation.screen.list.model.PaginationState
import com.vep1940.alkimiitestapp.sharedtest.fixture.CharacterFixture
import com.vep1940.alkimiitestapp.sharedtest.util.TestCoroutineExtension
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestCoroutineExtension::class)
internal class ListViewModelTest {

    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private val addCharacterToFavsUseCase: AddCharacterToFavsUseCase = mockk(relaxed = true)
    private val removeCharacterFromFavsUseCase: RemoveCharacterFromFavsUseCase = mockk(relaxed = true)
    private val getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase = mockk()

    companion object {
        private const val MIN_DELAY = 1L
        private const val CHECK_JOB_CANCELLING_REQUESTS_DELAY = 5L
    }

    @BeforeEach
    fun setUp() {
        every { getFavouriteCharactersUseCase() } returns flowOf(listOf())
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `GIVEN a recently created vm WHEN collecting screenState THEN first state is loading`() =
        runTest {
            val expected = ListScreenState.makeInitialState()

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                Exception().failure()
            }

            val sut: ListViewModel = makeSut()

            sut.screenState.test {
                val result = awaitItem()
                assertEquals(expected, result)

                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `GIVEN a recently created vm WHEN collecting screenState THEN first page is request`() =
        runTest {
            val expected = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays()
            )

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(1)

                coVerify(exactly = 1) {
                    getCharactersUseCase(gender = null)
                }

                val result = awaitItem()

                assertEquals(expected, result)
            }

        }

    @Test
    fun `GIVEN an update in either data list or fav list WHEN collecting screenState THEN result is combined`() =
        runTest {
            val expectedBeforeFavUpdate = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays()
            )
            val expectedAfterFavUpdate = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays(
                    favItemIndexes = listOf(
                        1
                    )
                )
            )
            val expectedAfterDataUpdate = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays(
                    size = 6,
                    favItemIndexes = listOf(1, 4)
                )
            )

            val favFlow = MutableStateFlow(listOf<Int>())
            every { getFavouriteCharactersUseCase() } returns favFlow
            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 3, offset = 3),
                    isFirstPage = false,
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(1)

                val resultBeforeFavUpdate = awaitItem()

                favFlow.emit(listOf(1))

                val resultAfterFavUpdate = awaitItem()

                favFlow.emit(listOf(1, 4))

                sut.getNextCharactersPage()

                skipItems(1)

                val resultAfterDataUpdate = awaitItem()

                assertEquals(expectedBeforeFavUpdate, resultBeforeFavUpdate)
                assertEquals(expectedAfterFavUpdate, resultAfterFavUpdate)
                assertEquals(expectedAfterDataUpdate, resultAfterDataUpdate)
            }
        }

    @Test
    fun `GIVEN get next characters page request WHEN already requesting new one THEN second request is discarded`() =
        runTest {

            val expected = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays(size = 2),
            )

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 2),
                    isFirstPage = true,
                ).success()
            } coAndThen {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 4),
                    isFirstPage = true,
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                clearMocks(getCharactersUseCase, answers = false)

                sut.getNextCharactersPage()
                sut.getNextCharactersPage()

                skipItems(count = 1)

                coVerify(exactly = 1) {
                    getCharactersUseCase(null)
                }

                val result = awaitItem()

                assertEquals(expected, result)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `WHEN getting next characters page request THEN loading status is pushed before response comes`() =
        runTest {

            val expected =
                ListScreenStateFixture.givenListScreenState(paginationState = PaginationState.LOADING)

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.getNextCharactersPage()

                val result = awaitItem()

                assertEquals(expected, result)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN an error WHEN getting next characters page request THEN error status is pushed`() =
        runTest {

            val expected =
                ListScreenStateFixture.givenListScreenState(paginationState = PaginationState.ERROR)

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(MIN_DELAY)
                Exception().failure()
            }

            val sut = makeSut()

            sut.screenState.test {
                skipItems(2)

                sut.getNextCharactersPage()

                skipItems(1)

                val result = awaitItem()

                assertEquals(expected, result)
            }
        }

    @Test
    fun `GIVEN a success being last page WHEN getting next characters page request THEN end status is pushed`() =
        runTest {

            val expectedNotLastPage =
                ListScreenStateFixture.givenListScreenState(paginationState = PaginationState.IDLE)

            val expectedLastPage =
                ListScreenStateFixture.givenListScreenState(paginationState = PaginationState.END)

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true, isLastPage = true).success()
            }

            val sut = makeSut()

            sut.screenState.test {
                skipItems(1)

                val resultNotLastPage = awaitItem()

                sut.getNextCharactersPage()

                skipItems(1)

                val resultLastPage = awaitItem()

                assertEquals(expectedNotLastPage, resultNotLastPage)
                assertEquals(expectedLastPage, resultLastPage)
            }
        }

    @Test
    fun `GIVEN a success being first page WHEN getting next characters page request THEN old data is replaced by new one`() =
        runTest {

            val expected = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays(size = 5)
            )

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 5),
                    isFirstPage = true,
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.getNextCharactersPage()

                skipItems(1)

                val result = awaitItem()

                assertEquals(expected, result)
            }
        }

    @Test
    fun `GIVEN a success not being first page WHEN getting next characters page request THEN new data is added to new one`() =
        runTest {

            val expected = ListScreenStateFixture.givenListScreenState(
                data = ListScreenStateFixture.givenCharacterListItemDisplays(size = 8)
            )

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            } coAndThen {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 5, offset = 3),
                    isFirstPage = false,
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.getNextCharactersPage()

                skipItems(1)

                val result = awaitItem()

                assertEquals(expected, result)
            }
        }

    @Test
    fun `WHEN applying a gender THEN old data is clear, loading status set, filter is applied or remove`() =
        runTest {

            val expectedFirstFilter = ListScreenStateFixture.givenListScreenState(
                filterSelected = GenderDisplay.FEMALE,
                data = emptyList(),
                paginationState = PaginationState.LOADING
            )
            val expectedSecondFilter = ListScreenStateFixture.givenListScreenState(
                filterSelected = GenderDisplay.GENDERLESS,
                data = emptyList(),
                paginationState = PaginationState.LOADING
            )
            val expectedThirdFilter = ListScreenStateFixture.givenListScreenState(
                filterSelected = null,
                data = emptyList(),
                paginationState = PaginationState.LOADING
            )

            coEvery { getCharactersUseCase(gender = any()) } coAnswers {
                delay(MIN_DELAY)
                CharacterFixture.givenCharacterPage(isFirstPage = true).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.applyGender(GenderDisplay.FEMALE)

                val resultFirstFilter = awaitItem()

                skipItems(1)

                sut.applyGender(GenderDisplay.GENDERLESS)

                val resultSecondFilter = awaitItem()

                skipItems(1)

                sut.applyGender(GenderDisplay.GENDERLESS)

                val resultThirdFilter = awaitItem()

                assertEquals(expectedFirstFilter, resultFirstFilter)
                assertEquals(expectedSecondFilter, resultSecondFilter)
                assertEquals(expectedThirdFilter, resultThirdFilter)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `WHEN applying a gender THEN next page is requested with the selected filter`() = runTest {

        coEvery { getCharactersUseCase(gender = any()) } coAnswers {
            delay(MIN_DELAY)
            CharacterFixture.givenCharacterPage(
                data = CharacterFixture.givenCharacterLiteList(size = 3),
            ).success()
        }

        val sut = makeSut()

        sut.screenState.test {

            sut.applyGender(GenderDisplay.FEMALE)

            sut.applyGender(GenderDisplay.GENDERLESS)

            sut.applyGender(GenderDisplay.GENDERLESS)

            coVerifyOrder {
                getCharactersUseCase(null)
                getCharactersUseCase(Gender.FEMALE)
                getCharactersUseCase(Gender.GENDERLESS)
                getCharactersUseCase(null)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN requested next characters page WHEN applying a gender THEN next page is canceled and replace by filtering`() =
        runTest {

            val expected = ListScreenStateFixture.givenListScreenState(
                filterSelected = GenderDisplay.FEMALE,
                data = ListScreenStateFixture.givenCharacterListItemDisplays(size = 4),
                paginationState = PaginationState.IDLE
            )

            coEvery { getCharactersUseCase(gender = null) } coAnswers {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 2),
                ).success()
            }
            coEvery { getCharactersUseCase(gender = Gender.FEMALE) } coAnswers {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 4),
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.getNextCharactersPage()

                skipItems(1)

                sut.applyGender(GenderDisplay.FEMALE)

                skipItems(1)

                val result = awaitItem()

                assertEquals(expected, result)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN requested gender WHEN applying a gender THEN first request is canceled and new one is applied`() =
        runTest {

            val expected = ListScreenStateFixture.givenListScreenState(
                filterSelected = GenderDisplay.GENDERLESS,
                data = ListScreenStateFixture.givenCharacterListItemDisplays(size = 4),
                paginationState = PaginationState.IDLE
            )

            coEvery { getCharactersUseCase(gender = any()) } coAnswers {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 2),
                ).success()
            }

            coEvery { getCharactersUseCase(gender = Gender.GENDERLESS) } coAnswers {
                delay(CHECK_JOB_CANCELLING_REQUESTS_DELAY)
                CharacterFixture.givenCharacterPage(
                    data = CharacterFixture.givenCharacterLiteList(size = 4),
                ).success()
            }

            val sut = makeSut()

            sut.screenState.test {

                skipItems(2)

                sut.getNextCharactersPage()

                skipItems(1)

                sut.applyGender(GenderDisplay.FEMALE)

                skipItems(1)

                sut.applyGender(GenderDisplay.GENDERLESS)

                skipItems(1)

                val result = awaitItem()

                assertEquals(expected, result)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN favourite character WHEN clicking on fav THEN remove fav use case is called`() =
        runTest {
            coEvery { getCharactersUseCase(gender = null) } returns Exception().failure()

            val sut = makeSut()

            sut.onClickFav(ListScreenStateFixture.givenCharacterListItemDisplay(isFavourite = true))

            verify(exactly = 1) {
                removeCharacterFromFavsUseCase(characterId = 1)
            }
        }

    @Test
    fun `GIVEN non favourite character WHEN clicking on fav THEN add fav use case is called`() =
        runTest {
            coEvery { getCharactersUseCase(gender = null) } returns Exception().failure()

            val sut = makeSut()

            sut.onClickFav(ListScreenStateFixture.givenCharacterListItemDisplay(isFavourite = false))

            verify(exactly = 1) {
                addCharacterToFavsUseCase(characterId = 1)
            }
        }

    private fun makeSut() = ListViewModel(
        getCharactersUseCase = getCharactersUseCase,
        addCharacterToFavsUseCase = addCharacterToFavsUseCase,
        removeCharacterFromFavsUseCase = removeCharacterFromFavsUseCase,
        getFavouriteCharactersUseCase = getFavouriteCharactersUseCase,
    )

}