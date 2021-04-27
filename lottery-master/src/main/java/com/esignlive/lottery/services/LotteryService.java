package com.esignlive.lottery.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.esignlive.lottery.domain.Buyer;
import com.esignlive.lottery.domain.Ticket;
import com.esignlive.lottery.domain.Winner;
import com.esignlive.lottery.exceptions.OverBuyingException;
import com.esignlive.lottery.exceptions.PlayingWithoutPurchaseException;
import com.esignlive.lottery.repositories.TicketsDAO;
import com.esignlive.lottery.utils.LotteryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LotteryService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryService.class);
	@Autowired
	TicketsDAO tickets;

	Set<String> currentPlayers;
	HashMap<Integer, String> currentWinners;


	public Buyer purchaseTicket(final String buyerName)
	{
		Optional<Ticket> ticket = tickets.getTickets().stream().filter(t -> !t.isPurchased()).findAny();
		if (ticket.isPresent())
		{
			ticket.get().setPurchased(true);
			ticket.get().setNameOfBuyer(buyerName);
			Buyer buyer = new Buyer(buyerName, ticket.get());
			LOGGER.info("{} bought a ticket number {}", buyerName, ticket.get().getTicketNumber());
			return buyer;
		}
		else
		{
			LOGGER.warn("No more tickets to sell");
			throw new OverBuyingException("We have just finished selling our last ticket! Try to be faster next time :)");
		}

	}

	public String participate(final String buyerName)
	{
		Optional<Ticket> ticket = tickets.getTickets().stream().filter(t -> t.getNameOfBuyer().equals(buyerName)).findAny();

		if (currentPlayers.contains(buyerName)){
			return "You get only register for a game once !";
		}

		if (ticket.isPresent() && !ticket.get().isUsed())
		{
			ticket.setTicketUsed = true;
			currentPlayers.add(buyerName);
			return "Your ticket has been successfully been registered for the game !";
		}
		else
		{
			LOGGER.warn("Please buy a ticket to play first!");
			throw new PlayingWithoutPurchaseException("Please buy a ticket to play first :)");
		}

	}

	public Winner generousDraw()
	{
		LOGGER.info("The Lottery Game is drawing for winners");
		Ticket winningTicket1 = tickets.getTickets().stream().filter(t -> t.isTicketUsed()).findAny().get();

		Winner winner = new Winner(winningTicket1.getNameOfBuyer(), winningTicket1,
				winningTicket1.isPurchased() ? LotteryUtil.calculatePrize(1) : 0, Winner.Place.FIRST);

		populateCurrentWinners(currentWinners, winner);
		LOGGER.info("Draw is complete");
		LOGGER.info("The Lottery Game is resetting the Tickets");
		tickets.getTickets().clear();
		currentPlayers.clear();
		tickets.generateTickets();

		return winner;
	}

	protected void populateCurrentWinners(HashMap<Integer, Winner> currentWinners, Winner newWinner)
	{
		// storing the new winner at 0th location and removing the data for the 7th day after every lottery game
		for (int i=0;i<7;i++){

			Winner temp = currentWinners.get(i);
			currentWinners.put(i, newWinner);
			newWinner = temp;
		}


	}

	public void resetProcess()
	{
		tickets.getTickets().clear();
		tickets.generateTickets();
		currentWinners.clear();
		currentPlayers.clear();
		LOGGER.info("The Lottery Game is reset");
	}



	public HashMap<Integer, String> getCurrentWinners()
	{
		HashMap<Integer, String> result = this.currentWinners;
		if (MapUtils.isNotEmpty(result))
		{
			LOGGER.info("Winners from the last week's draws were: ");
			LotteryUtil.logWinners(this.currentWinners);
		}
		else
		{
			LOGGER.info("There are no winners before a draw");
		}

		return result;
	}

	public String getNextDraw(){

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		Date date = calendar.getTime();
		int day = calendar.get(Calendar.DATE);


		String result = "Thanks for showing interest in the lucky draw . The next draw is happening" +
				" tonight at 8PM . The reward for the show is {reward} .";

		String reward = "";

		if (day>=1 && day<=5){
			reward = "LED TV";
		}else if(day>=6 && day<=10){
			reward = "MOTORCYCLE";
		}else if(day>=11 && day<=15){
			reward = "LAPTOP";
		}else if(day>=16 && day<=20){
			reward = "CAR";
		}else if(day>=21 && day<=31){
			reward = "BAG";
		}

		result.replace("{reward}", reward);

		return result;
	}
}

