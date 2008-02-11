setwd("/Users/zbrown/workspace/ModelFrame/output")

cols <- topo.colors(10)

postscript(file=paste("output.ps", sep="", collapse=""), onefile = T, paper="letter")

for(i in 1:6) {

	j <- abs(i - 7)
	
	file <- paste("output_hvsba", j, ".txt", sep="", collapse="")
	
	data <- read.table(file, header = T, sep = "\t")
	
	if(i == 1) {
		plot(data$h, data$tb/trunc(j/100*250*250), type = "l", col=cols[j])
	}
	else {q
		points(data$h, data$tb/trunc(j/100*250*250), type="l", col=cols[j])
	}
}
title(main = paste("No. Humans vs Bite Average", sep="", collapse=""))
dev.off()


per.name <- paste(1:10,"%", sep = "")
plot(1:10,1:10,type = "n", main = "Legend", xlab = "", ylab = "", axes = F)
points(rep(1,10), 1:10, col = cols, pch = 19, cex = 2)
text(rep(2,10),1:10,labels = per.name, cex = 1.5)