package com.esignlive.lottery.utils;

import java.util.Set;

import com.esignlive.lottery.domain.Winner;
import com.esignlive.lottery.repositories.MoneyPot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotteryUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryUtil.class);
	/**
	 * prints the winners
	 *
	 * @param winners
	 */
	public static void logWinners(HashMap<Integer, String> winners)
	{
		int i = 1;
		for (Winner winner : winners.getValues())
		{
			LOGGER.info("-------------------------------------------------------");
			LOGGER.info("winner{} is: {}", i, winner.getBuyerName());
			LOGGER.info("ticket number: {}", winner.getTicket().getTicketNumber());
			LOGGER.info("prize is: {} $", winner.getTicket().isPurchased() ? winner.getPrize() : 0);
			LOGGER.info("-------------------------------------------------------");
			i++;
		}
	}

	/**
	 * Calculate the prize on half of the money pot
	 * 75 % for sequence 1
	 * 15 % for sequence 2
	 * 10 % for sequence 3
	 *
	 * @param i
	 * 		the winner sequence
	 * @return the prize amount
	 */
	public static String calculatePrize(int i)
	{
		String result = "";
		if (i == 1)
			result = "AC";
		if (i == 2)
			result = "TV";
		if (i == 3)
			result = "Fridge";
		return result;
	}

}
