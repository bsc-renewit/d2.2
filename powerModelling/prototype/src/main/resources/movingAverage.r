#!/usr/bin/env Rscript
args <- commandArgs(trailingOnly = TRUE)
file = read.csv(args[1], check.names=FALSE)
window = strtoi(args[2])
file_output = args[3]

# x: the vector
# n: the number of samples
# centered: if FALSE, then average current sample and previous (n-1) samples
#           if TRUE, then average symmetrically in past and future. (If n is even, use one more sample from future.)
movingAverage <- function(x, n, centered) {

  if (centered) {
    before <- floor  ((n-1)/2)
    after  <- ceiling((n-1)/2)
  } else {
    before <- n-1
    after  <- 0
  }

  # Track the sum and count of number of non-NA items
  s     <- rep(0, length(x))
  count <- rep(0, length(x))

  # Add the centered data
  new <- x
  # Add to count list wherever there isn't a
  count <- count + !is.na(new)
  # Now replace NA_s with 0_s and add to total
  new[is.na(new)] <- 0
  s <- s + new

  # Add the data from before
  i <- 1
  while (i <= before) {
    # This is the vector with offset values to add
    new   <- c(rep(NA, i), x[1:(length(x)-i)])

    count <- count + !is.na(new)
    new[is.na(new)] <- 0
    s <- s + new

    i <- i+1
  }

  # Add the data from after
  i <- 1
  while (i <= after) {
    # This is the vector with offset values to add
    new   <- c(x[(i+1):length(x)], rep(NA, i))

    count <- count + !is.na(new)
    new[is.na(new)] <- 0
    s <- s + new

    i <- i+1
  }

  # return sum divided by count
  s/count
}


filtered_file <- mapply(movingAverage, file, window, TRUE)

if("timestamp" %in% colnames(filtered_file)){
  filtered_file[,"timestamp"] <- file$timestamp
}
if("Core_1CPU" %in% colnames(filtered_file)){
  filtered_file[,"Core_1CPU"] <- file$Core_1CPU
}
if("Core_2CPU" %in% colnames(filtered_file)){
  filtered_file[,"Core_2CPU"] <- file$Core_2CPU
}
if("numSockets" %in% colnames(filtered_file)){
  filtered_file[,"numSockets"] <- file$numSockets
}
write.csv(filtered_file, file = file_output ,row.names=FALSE)



#UU <- data.frame(apply(training, 2, movingAverage))
#x <- 1:nrow(training)
#plot(x, training$cpu_idle, type="l", col=grey(.5))
#grid()
#lines(x, UU$cpu_idle, col="green", lwd=2)
