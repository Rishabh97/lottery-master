package com.esignlive.lottery.domain;

public class Ticket
{
	private boolean purchased;
	private String nameOfBuyer;
	private long ticketNumber;
	private boolean ticketUsed;

	public Ticket(final long ticketNumber, final String name, final boolean isPurchased, final boolean isUsed)
	{
		this.purchased = isPurchased;
		this.ticketNumber = ticketNumber;
		this.nameOfBuyer = name;
		this.ticketUsed = isUsed;
	}

	public boolean isTicketUsed() {
		return ticketUsed;
	}

	public void setTicketUsed(boolean ticketUsed) {
		this.ticketUsed = ticketUsed;
	}

	public boolean isPurchased()
	{
		return purchased;
	}

	public void setPurchased(final boolean purchased)
	{
		this.purchased = purchased;
	}

	public String getNameOfBuyer()
	{
		return nameOfBuyer;
	}

	public void setNameOfBuyer(final String nameOfBuyer)
	{
		this.nameOfBuyer = nameOfBuyer;
	}

	public long getTicketNumber()
	{
		return ticketNumber;
	}

	public void setTicketNumber(final long ticketNumber)
	{
		this.ticketNumber = ticketNumber;
	}

	@Override
	public String toString()
	{
		return "Ticket{" +
				"purchased=" + purchased +
				", nameOfBuyer='" + nameOfBuyer + '\'' +
				", ticketNumber=" + ticketNumber +
				'}';
	}
}
