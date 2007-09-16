setwd("/Users/zbrown/workspace/ModelFrame/output")

postscript(file="output.ps", onefile = T, paper="letter")

for(i in 1:100)
{
	file <- paste("output_m", i, ".txt", sep="", collapse="")
	
	data <- read.table(file, header = T, sep = "\t")
	
	bites <- plot(data$t, data$b, type = "l")
	title(main = paste("Bites (b) vs Time (t) - run #: ", i, sep="", collapse=""))
	
	wait <- plot(data$t, data$w, type = "l", col = "red")
	title(main = paste("Wait Amount (w) vs Time (t) - run #: ", i, sep="", collapse=""))
	
	if(i %/% 100 == 0) {
		bites_avg <- plot(data$t, data$ba, type = "l", col = "blue")
		title(main = paste("Bites Avg (ba) vs Time (t) - run #: ", i, sep="", collapse=""))
	}
	
	
}

dev.off()

	