package me.duncanruns.beachfilter.runner;

import me.duncanruns.beachfilter.cryptography.DRandInfo;
import me.duncanruns.beachfilter.cryptography.DRandRequester;
import me.duncanruns.beachfilter.filter.BeachFilter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

public class FSGBeach {
    private static final Random RANDOM = new SecureRandom();

    public static void main(String[] args) {

        try {
            FilterResult result = findSeed();
            System.out.println("Seed: " + result.getWorldSeed());
            System.out.println("Token: " + result.toToken());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static FilterResult findSeed() throws IOException, NoSuchAlgorithmException {
        final boolean filterForest = true;

        DRandInfo dRandInfo = new DRandRequester().get("latest");
        Instant instant = Instant.now();
        long startTime = instant.getEpochSecond() * (1_000_000_000) + instant.getNano();
        long randomFactor = RANDOM.nextLong();
        String seedString = (startTime + randomFactor) + dRandInfo.randomness;

        // Java SHA256
        // https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(seedString.getBytes(StandardCharsets.UTF_8));

        long initialSeed = new BigInteger(hash).longValue();

        Random random = new Random(initialSeed);

        int checks = 0;

        while (true) {
            checks++;
            long seed = random.nextLong() + 1;
            if (BeachFilter.test(seed, filterForest)) {
                return new FilterResult(seed, startTime, checks, dRandInfo.round, randomFactor);
            }
        }
    }
}
