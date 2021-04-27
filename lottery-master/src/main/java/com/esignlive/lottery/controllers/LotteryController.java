package com.esignlive.lottery.controllers;


import javax.validation.constraints.NotNull;

import java.util.Set;

import com.esignlive.lottery.domain.Buyer;
import com.esignlive.lottery.domain.Ticket;
import com.esignlive.lottery.domain.Winner;
import com.esignlive.lottery.exceptions.OverBuyingException;
import com.esignlive.lottery.exceptions.PlayingWithoutPurchaseException;
import com.esignlive.lottery.services.LotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lottery")
public class LotteryController
{
	private final Logger LOGGER = LoggerFactory.getLogger(LotteryController.class);

	@Autowired
	private LotteryService lotteryService;

	@RequestMapping(value = "/purchase/{nameOfBuyer}", method = RequestMethod.POST)
	public ResponseEntity<Buyer> purchaseTicket(@PathVariable @NotNull String nameOfBuyer)
	{
		try
		{
			return new ResponseEntity<>(lotteryService.purchaseTicket(nameOfBuyer), HttpStatus.CREATED);
		}
		catch (OverBuyingException overBuyingException)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	@RequestMapping(value = "/registerForGame/{nameOfBuyer}", method = RequestMethod.POST)
	public ResponseEntity<Buyer> participate(@PathVariable @NotNull String nameOfBuyer)
	{
		try
		{
			return new ResponseEntity<>(lotteryService.participate(nameOfBuyer), HttpStatus.CREATED);
		}
		catch (PlayingWithoutPurchaseException playingWithoutPurchaseException)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}



	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity resetProcess()
	{
		lotteryService.resetProcess();
		return ResponseEntity.ok("The game is reset");
	}

	@RequestMapping(value = "/draw", method = RequestMethod.POST)
	public ResponseEntity<Winner> draw()
	{
		Winner winners = lotteryService.generousDraw();
		return new ResponseEntity<>(winners, HttpStatus.OK);
	}

	@RequestMapping(value = "/winnersInPastWeek", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Integer, Winner>> getCurrentWinners()
	{
		return new ResponseEntity<>(lotteryService.getCurrentWinners(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getNextDrawDetails", method = RequestMethod.GET)
	public String getNextDraw(){
		return new ResponseEntity<>(lotteryService.getNextDraw(), HttpStatus.OK);
	}

}
