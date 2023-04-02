package com.pooyan.dev.data.usecase

import com.pooyan.dev.data.repository.CardRepository
import com.pooyan.dev.domain.GetCardsUseCase
import com.pooyan.dev.model.Card
import javax.inject.Inject

/**
 * Implementation of [GetCardsUseCase] interface. This class provides a method to get a shuffled card
 * from the repository, without repeating previously played cards.
 * @property cardRepository An instance of [CardRepository] used to interact with the data source.
 * @property cardList A list of [Card] objects to cache the cards received from the repository.
 */
class GetCardsUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) :
    GetCardsUseCase {

    private val cardList = mutableListOf<Card>()

    override suspend operator fun invoke(): Card? {
        val playedCardsId = cardRepository.getPlayedCardsId()

        if (cardList.isEmpty()) {
            cardList.addAll(cardRepository.getCards())
        }

        val shuffledCard =
            cardList.filter { !playedCardsId.contains(it.id) }.shuffled().firstOrNull()

        if (shuffledCard != null) {
            cardRepository.insertPlayedCard(shuffledCard.id)
        }
        return shuffledCard
    }
}