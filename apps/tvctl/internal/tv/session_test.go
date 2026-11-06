package tv

import (
	"errors"
	"io"
	"os"
	"path/filepath"
	"testing"
)

func TestAFailedPullLeavesTheLocalFileAsItWas(t *testing.T) {
	local := filepath.Join(t.TempDir(), "settings.txt")
	if err := os.WriteFile(local, []byte("earlier copy"), 0o644); err != nil {
		t.Fatal(err)
	}

	linkLost := errors.New("connection reset")
	err := writeThroughPartFile(local, func(file io.Writer) error {
		io.WriteString(file, "half")
		return linkLost
	})
	if !errors.Is(err, linkLost) {
		t.Fatalf("err = %v", err)
	}

	if data, _ := os.ReadFile(local); string(data) != "earlier copy" {
		t.Errorf("local file = %q", data)
	}
	if _, err := os.Stat(local + ".part"); !os.IsNotExist(err) {
		t.Errorf("part file left behind: %v", err)
	}
}

func TestACompletePullReplacesTheLocalFile(t *testing.T) {
	local := filepath.Join(t.TempDir(), "new", "settings.txt")
	err := writeThroughPartFile(local, func(file io.Writer) error {
		_, err := io.WriteString(file, "fresh")
		return err
	})
	if err != nil {
		t.Fatal(err)
	}

	if data, _ := os.ReadFile(local); string(data) != "fresh" {
		t.Errorf("local file = %q", data)
	}
}
